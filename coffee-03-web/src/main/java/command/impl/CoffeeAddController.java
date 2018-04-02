package command.impl;

import command.Controller;
import entities.CoffeeType;
import entities.enums.DisabledFlag;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import services.ICoffeeTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@org.springframework.stereotype.Controller
public class CoffeeAddController implements Controller {
    @Autowired
    private ICoffeeTypeService coffeeTypeService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        final double DEFAULT_PRICE = 0;

        // del after debug
        System.out.println("start CoffeeAddController");

        // get income parameters
        String disabledParameter = req.getParameter("disabled");
        DisabledFlag disabledFlag = disabledParameter != null && "on".equalsIgnoreCase(disabledParameter)
                ? DisabledFlag.Y
                : DisabledFlag.N;

        String typeName = req.getParameter("typeName");
        typeName = typeName != null && typeName.isEmpty() ? null : typeName;
        double price = NumberUtils.toDouble(req.getParameter("price"), DEFAULT_PRICE);
        price = price < 0 ? DEFAULT_PRICE : price;

        // create CoffeeType
        CoffeeType coffeeType = new CoffeeType();
        coffeeType.setDisabledFlag(disabledFlag);
        if (typeName != null) {
            coffeeType.setTypeName(typeName);
        }
        if (price != DEFAULT_PRICE) {
            coffeeType.setPrice(price);
        }
        // save coffeeType in session
        req.getSession().setAttribute("coffeeTypeForAdd", coffeeType);

        if (typeName == null && price == DEFAULT_PRICE) {
            // first time on page, refer to MAIN_PAGE.
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);

        } else {
            if (typeName != null && price != DEFAULT_PRICE) {
                // income parameters are valid, save CoffeeType in Db
                coffeeTypeService.add(coffeeType);

                // save in request
                req.setAttribute("afterChange", "on");

                // coffeeType updated -> refer to CoffeeType list
                req.getRequestDispatcher("/frontController?command=coffee").forward(req, resp);

            } else {
                // income parameters are invalid
                // save errorMessage in request
                req.setAttribute("coffeeChangeErrorMsg", "Invalid parameters");
                // again enter parameters, refer to MAIN_PAGE.
                req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
            }
        }
    }
}

