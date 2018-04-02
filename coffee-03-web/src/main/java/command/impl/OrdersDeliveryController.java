package command.impl;

import command.Controller;
import entities.CoffeeOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import services.ICoffeeOrderService;
import services.ICoffeeTypeService;
import vo.CoffeeOrderAndCost;
import vo.CoffeeOrderItemVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@org.springframework.stereotype.Controller
public class OrdersDeliveryController implements Controller {
    @Autowired
    private ICoffeeOrderService coffeeOrderService;
    @Autowired
    private ICoffeeTypeService coffeeTypeService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        final String DEFAULT_STRING = "NOT ASSIGNED";

        // del after debug
        System.out.println("start OrdersDeliveryController");

        // validate income parameters from form
        String customerName = req.getParameter("customerName");
        customerName = customerName != null && customerName.isEmpty() ? null : customerName;

        String deliveryAddress = req.getParameter("deliveryAddress");
        deliveryAddress = deliveryAddress != null && deliveryAddress.isEmpty() ? null : deliveryAddress;
        List<CoffeeOrderItemVo> coffeeOrderItemVoList = req.getSession()
                                                           .getAttribute("coffeeOrderItemVoList") != null
                ? (List<CoffeeOrderItemVo>) req.getSession()
                                               .getAttribute("coffeeOrderItemVoList")
                : null;
        // del after debug
        System.out.println("coffeeOrderItemVoList: " + coffeeOrderItemVoList);

        if (deliveryAddress == null) {
            // del after debug
            System.out.println("deliveryAddress == null");
            // save attribute in request
            req.setAttribute("customerName", customerName);

            // save error message in request
            req.setAttribute("deliveryErrorMsg", "Enter delivery address, please.");
            // not exist any delivery Address - try again input
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        } else {
            // create order & save in DB
            customerName = customerName == null ? DEFAULT_STRING : customerName;
            CoffeeOrderAndCost coffeeOrderAndCost = coffeeOrderService.makeOrder(customerName,
                    deliveryAddress, transformer(coffeeOrderItemVoList));
            if (coffeeOrderAndCost == null) {
                // del after debug
                System.out.println("coffeeOrderAndCost == null");

                // no Order items exist
                // save error message in request
                req.setAttribute("orderErrorMsg", "Choose coffee, please.");
                // empty order items list - refer to choose coffee
                req.getRequestDispatcher("/frontController?command=orders").forward(req, resp);
            } else {
                // del after debug
                System.out.println("coffeeOrderAndCost: " + coffeeOrderAndCost);

                // save coffeeOrderAndCost in session
                req.getSession().setAttribute("coffeeOrderAndCost", coffeeOrderAndCost);
                // order successfully create - show order
                req.getRequestDispatcher("/frontController?command=showOrder").forward(req, resp);
            }
        }
    }

    private List<CoffeeOrderItem> transformer(List<CoffeeOrderItemVo> coffeeOrderItemVoList) {
        List<CoffeeOrderItem> coffeeOrderItemList = new LinkedList<>();
        if (coffeeOrderItemVoList != null) {
            for (CoffeeOrderItemVo itemVo : coffeeOrderItemVoList) {
                CoffeeOrderItem coffeeOrderItem = new CoffeeOrderItem();
                coffeeOrderItem.setType(coffeeTypeService.get(itemVo.getCoffeeTypeId()));
                coffeeOrderItem.setQuantity(itemVo.getQuantity());
                coffeeOrderItemList.add(coffeeOrderItem);
            }
        }
        return coffeeOrderItemList;
    }
}
