package command.impl;

import command.Controller;
import entities.CoffeeType;
import entities.enums.DisabledFlag;
import org.springframework.beans.factory.annotation.Autowired;
import services.ICoffeeTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Controller
public class CoffeeController implements Controller {
    @Autowired
    private ICoffeeTypeService coffeeTypeService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        final int DEFAULT_ID = -1;

        // del after debug
        System.out.println("start CoffeeController");

        // take coffeeTypes from Db
        List<CoffeeType> coffeeTypeList = coffeeTypeService.getAll();

        if ("GET".equals(req.getMethod())) {
            // first come on the page
            // coffeeTypes save in request
            req.setAttribute("coffeeTypeList", coffeeTypeList);
            // show all CoffeeTypes, refer to MAIN_PAGE.
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);

            return;
        }


        String afterChange = req.getAttribute("afterChange") != null
                ? (String) req.getAttribute("afterChange")
                : null;
        if ("on".equals(afterChange)) {
            // first come on the page after changeCoffeeType
            // coffeeTypes save in request
            req.setAttribute("coffeeTypeList", coffeeTypeList);
            // show all CoffeeTypes, refer to MAIN_PAGE.
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);

            return;
        }

        // check income parameters
        boolean changeDisabledFlag = false;
        boolean changeFlag = false;
        int coffeeTypeIdForChange = DEFAULT_ID;
        for (CoffeeType coffeeType : coffeeTypeList) {
            // determine the type of coffee for change
            String changeParameter = req.getParameter("coffeeTypeId" + String.valueOf(coffeeType.getId()));
            changeFlag = changeFlag || changeParameter != null;

            if (changeFlag && coffeeTypeIdForChange == DEFAULT_ID) {
                coffeeTypeIdForChange = coffeeType.getId();
            }

            // check disabled flag & change in CoffeeType if necessary
            String disabledParameter = req.getParameter("disabled" + String.valueOf(coffeeType.getId()));
            if (disabledParameter != null && disabledParameter.equalsIgnoreCase("on")) {
                if (DisabledFlag.N.equals(coffeeType.getDisabledFlag())) {
                    // change DisabledFlag on "Y" for coffeeType in Db
                    coffeeType.setDisabledFlag(DisabledFlag.Y);
                    coffeeTypeService.update(coffeeType);
                    changeDisabledFlag = true;
                }
            }
            if (disabledParameter == null || ! "on".equalsIgnoreCase(disabledParameter)) {
                if (DisabledFlag.Y.equals(coffeeType.getDisabledFlag())) {
                    // change DisabledFlag on "N" for coffeeType in Db
                    coffeeType.setDisabledFlag(DisabledFlag.N);
                    coffeeTypeService.update(coffeeType);
                    changeDisabledFlag = true;
                }
            }
        }

        if (changeFlag) {
            // CoffeeType with id coffeeTypeIdForChange - mast be changed
            CoffeeType coffeeTypeForChange = coffeeTypeService.get(coffeeTypeIdForChange);
            // coffeeTypeForChange save in session
            req.getSession().setAttribute("coffeeTypeForChange", coffeeTypeForChange);

            // refer to CoffeeType change "/frontController?command=changeCoffee"
            req.getRequestDispatcher("/frontController?command=changeCoffee").forward(req, resp);
        } else {
            // check if DisabledFlag was changed
            if (changeDisabledFlag) {
                // take changed coffeeTypes from Db
                coffeeTypeList = coffeeTypeService.getAll();
            }
            // coffeeTypes save in request
            req.setAttribute("coffeeTypeList", coffeeTypeList);
            // show all CoffeeTypes, refer to MAIN_PAGE.
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        }
    }
}
