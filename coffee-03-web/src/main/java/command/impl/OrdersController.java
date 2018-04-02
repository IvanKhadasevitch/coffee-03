package command.impl;

import command.Controller;
import entities.CoffeeType;
import entities.enums.DisabledFlag;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import services.ICoffeeTypeService;
import services.IConfigurationService;
import vo.CoffeeOrderItemVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@org.springframework.stereotype.Controller
public class OrdersController implements Controller {
    @Autowired
    private ICoffeeTypeService coffeeTypeService;
    @Autowired
    private IConfigurationService configurationService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        // del after debug
        System.out.println("start OrdersController");

        // get number of freeCup from Db
        String freeCup = configurationService.getValue("n");
        if (freeCup != null) {
            // save freeCup in session
            req.getSession().setAttribute("freeCup", freeCup);
        }

        // get coffeeType list from Db where DisabledFlag.N for UI
        List<CoffeeType> coffeeTypeList = coffeeTypeService.getAllForDisabledFlag(DisabledFlag.N);

        // save coffeeTypeList in request
        req.setAttribute("coffeeTypeList", coffeeTypeList);

        // validate income parameters from form
        List<CoffeeOrderItemVo> coffeeOrderItemVoList = new LinkedList<>();
        for (CoffeeType coffeeType : coffeeTypeList) {
            String parameterName = "orderQuantity" + String.valueOf(coffeeType.getId());
            int orderQuantity = NumberUtils.toInt(req.getParameter(parameterName), 0);
            orderQuantity = orderQuantity < 0 ? 0 : orderQuantity;
            // save not null order item in CoffeeOrderItemVo
            if (orderQuantity != 0) {
                CoffeeOrderItemVo coffeeOrderItemVo = new CoffeeOrderItemVo();
                coffeeOrderItemVo.setCoffeeTypeId(coffeeType.getId());
                coffeeOrderItemVo.setCoffeeTypeName(coffeeType.getTypeName());
                coffeeOrderItemVo.setCoffeePrice(coffeeType.getPrice());
                coffeeOrderItemVo.setQuantity(orderQuantity);
                coffeeOrderItemVoList.add(coffeeOrderItemVo);
            }
        }

        // check if any order item exist
        if (coffeeOrderItemVoList.isEmpty()) {
            // del after debug
            System.out.println("coffeeOrderItemVoList.isEmpty(): true");

            // not exist any order item, refer to MAIN_PAGE.
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        } else {
            // del after debug
            System.out.println("coffeeOrderItemVoList.isEmpty(): false");

            // save coffeeOrderItemVoList in session
            req.getSession().setAttribute("coffeeOrderItemVoList", coffeeOrderItemVoList);

            // refer to delivery order "/frontController?command=delivery"
            req.getRequestDispatcher("/frontController?command=delivery").forward(req, resp);

        }
    }
}
