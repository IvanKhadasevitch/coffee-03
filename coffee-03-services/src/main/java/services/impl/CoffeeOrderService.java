package services.impl;

import dao.ICoffeeOrderDao;
import dao.ICoffeeOrderItemDao;
import dao.ICoffeeTypeDao;
import entities.CoffeeOrder;
import entities.CoffeeOrderItem;
import entities.CoffeeType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.ICoffeeOrderService;
import services.IConfigurationService;
import services.ServiceException;
import vo.CoffeeOrderAndCost;
import vo.Cost;

import java.util.Date;
import java.util.List;

@Service
@Transactional(transactionManager = "txManager")
public class CoffeeOrderService extends BaseService<CoffeeOrder> implements ICoffeeOrderService {
    private static Logger log = Logger.getLogger(CoffeeOrderService.class);

    private ICoffeeOrderDao coffeeOrderDao;
//    private ICoffeeOrderItemDao coffeeOrderItemDao;
    private ICoffeeTypeDao coffeeTypeDao;
    private IConfigurationService configurationService;

    @Autowired
    public CoffeeOrderService(ICoffeeOrderDao coffeeOrderDao,
//                              ICoffeeOrderItemDao coffeeOrderItemDao,
                              ICoffeeTypeDao coffeeTypeDao, IConfigurationService configurationService) {
        super();
        this.coffeeOrderDao = coffeeOrderDao;
//        this.coffeeOrderItemDao = coffeeOrderItemDao;
        this.coffeeTypeDao = coffeeTypeDao;
        this.configurationService = configurationService;
    }

    /**
     * Forms an order for not empty list of order elements and deliveryAddress != null,
     * calculates the cost of delivery and ordered coffee. If the formation is successful,
     * it saves the order and the list of order items in the database
     *
     * @param customerName        determines the name of the customer
     * @param deliveryAddress     determines the delivery address
     * @param coffeeOrderItemList list of order elements
     * @return an CoffeeOrderAndCost object that contains order stored in the database and object
     * Cost that contains cost of delivery and ordered coffee,
     * or return null if the order is not generated
     */
    @Override
    public CoffeeOrderAndCost makeOrder(String customerName, String deliveryAddress,
                                        List<CoffeeOrderItem> coffeeOrderItemList) {
        if (deliveryAddress != null && coffeeOrderItemList != null &&
                !coffeeOrderItemList.isEmpty()) {
            CoffeeOrder coffeeOrder = new CoffeeOrder();

//            this.startTransaction();

            // form Order data & save in Db
            Date currentDate = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(currentDate.getTime());
            coffeeOrder.setOrderDate(timestamp);
            coffeeOrder.setCustomerName(customerName);
            coffeeOrder.setDeliveryAddress(deliveryAddress);
            Cost cost = costCalculate(coffeeOrderItemList);
            // cost was calculated successfully, continue work
            coffeeOrder.setCost(cost.getCoffeeTotalCost() + cost.getDeliveryCost());
            // add OrderItems to the Order
            coffeeOrderItemList.forEach(coffeeOrder::addCoffeeOrderItem);
//            for (CoffeeOrderItem item : coffeeOrderItemList) {
//                coffeeOrder.addCoffeeOrderItem(item);
//            }

            // safe Order in Db, Order Items will be saved by cascade
            coffeeOrder = coffeeOrderDao.add(coffeeOrder);

            // save Order Items in Db
//            for (CoffeeOrderItem item : coffeeOrderItemList) {
//                item.setOrderId(coffeeOrder.getId());
//                coffeeOrderItemDao.save(item);
//            }

//            this.commit();
//            this.stopTransaction();

            CoffeeOrderAndCost coffeeOrderAndCost = new CoffeeOrderAndCost();
            coffeeOrderAndCost.setCoffeeOrder(coffeeOrder);
            coffeeOrderAndCost.setCost(cost);

            return coffeeOrderAndCost;
        } else {
            return null;
        }
    }

    private Cost costCalculate(List<CoffeeOrderItem> coffeeOrderItemList) throws ServiceException {

        // take configuration for free cup
        String parameterN = configurationService.getValue("n");
        int freeCupN;
        if (parameterN == null) {
            throw new ServiceException("There is no default configuration or configuration " +
                    "stored in the database for the key: [n]");
        } else {
            freeCupN = Integer.valueOf(parameterN);
        }
        if (freeCupN == 0) {
            throw new ServiceException("Configuration with key [n] can't has [zero] value! ");
        }

        // take configuration for min Order Total Sum for free delivery
        String parameterX = configurationService.getValue("x");
        double minOrderTotalForFreeDeliveryX;
        if (parameterX == null) {
            throw new ServiceException("There is no default configuration or configuration " +
                    "stored in the database for the key: [x]");
        } else {
            minOrderTotalForFreeDeliveryX = Double.valueOf(parameterX);
        }
        // take configuration for delivery price
        String parameterM = configurationService.getValue("m");
        double deliveryPriceM;
        if (parameterM == null) {
            throw new ServiceException("There is no default configuration or configuration " +
                    "stored in the database for the key: [m]");
        } else {
            deliveryPriceM = Double.valueOf(parameterM);
        }

        // calculate cost for Order items list
        int cupsNumberInOrder = 0;
        double costCoffee = 0;
        for (CoffeeOrderItem item : coffeeOrderItemList) {
            CoffeeType coffeeType = coffeeTypeDao.get(item.getType() != null
                    ? item.getType().getId()
                    : -1);
            if (coffeeType == null) {
                throw new ServiceException("There is no in database CoffeeType with id: " +
                        item.getId());
            }
            for (int i = 0; i < item.getQuantity(); i++) {
                cupsNumberInOrder++;
                if ( (cupsNumberInOrder % freeCupN) != 0 ) {
                    // every n cup is free
                    costCoffee += coffeeType.getPrice();
                }
            }
        }

        Cost cost = new Cost();
        cost.setCoffeeTotalCost(costCoffee);
        // if order costCoffee more then minOrderTotalForFreeDeliveryX - delivery free
        cost.setDeliveryCost(costCoffee > minOrderTotalForFreeDeliveryX ? 0.0 : deliveryPriceM);

        return cost;
    }
}
