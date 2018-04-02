package services.impl;

import dao.ICoffeeOrderItemDao;
import entities.CoffeeOrderItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.ICoffeeOrderItemService;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional(transactionManager = "txManager")
public class CoffeeOrderItemService extends BaseService<CoffeeOrderItem>
        implements ICoffeeOrderItemService {
    private static Logger log = Logger.getLogger(CoffeeOrderItemService.class);

    @Autowired
    private ICoffeeOrderItemDao coffeeOrderItemDao;

    public CoffeeOrderItemService() {
        super();
    }

    /**
     *  get all records with type <CoffeeOrderItem> from data base for definite CoffeeOrder
     *
     * @param orderId determine id of CoffeeOrder in database
     * @return a list of all records of type <CoffeeOrderItem> from the database
     *         for definite CoffeeOrder or empty list if there are no entries
     */
    @Override
    public List<CoffeeOrderItem> getAllForOrderId(Serializable orderId) {
        return coffeeOrderItemDao.getAllForOrderId(orderId);
    }
}
