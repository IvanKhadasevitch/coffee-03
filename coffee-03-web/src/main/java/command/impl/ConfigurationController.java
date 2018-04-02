package command.impl;

import command.Controller;
import entities.Configuration;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import services.IConfigurationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@org.springframework.stereotype.Controller
public class ConfigurationController implements Controller {
    @Autowired
    private IConfigurationService configurationService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        final int DEFAULT_FREE_CUP = -1;
        final double DEFAULT_DELIVERY = -1;

        // del after debug
        System.out.println("start ConfigurationController");

        // check freeCup
        int freeCup = NumberUtils.toInt(req.getParameter("freeCup"), DEFAULT_FREE_CUP);
        if (freeCup < 1) {
            // use value from Db or default free cup value
            freeCup = Integer.valueOf(configurationService.getValue("n"));

        } else {
            // check exist "n" configuration in Db
            configurationSaveOrUpdate(String.valueOf(freeCup), "n");
        }
        // save freeCup in session
        req.getSession().setAttribute("freeCup", freeCup);

        // check freeDelivery
        double freeDelivery = NumberUtils.toDouble(req.getParameter("freeDelivery"), DEFAULT_DELIVERY);
        if (freeDelivery < 0) {
            // use value from Db or default value
            freeDelivery = Double.valueOf(configurationService.getValue("x"));

        } else {
            // check exist "x" configuration in Db
            configurationSaveOrUpdate(String.valueOf(freeDelivery), "x");
        }
        // save freeDelivery in session
        req.getSession().setAttribute("freeDelivery", freeDelivery);

        // check deliveryPrice
        double deliveryPrice = NumberUtils.toDouble(req.getParameter("deliveryPrice"), DEFAULT_DELIVERY);
        if (deliveryPrice < 0) {
            // use value from Db or default value
            deliveryPrice = Double.valueOf(configurationService.getValue("m"));

        } else {
            // check exist "m" configuration in Db
            configurationSaveOrUpdate(String.valueOf(deliveryPrice), "m");
        }
        // save deliveryPrice in session
        req.getSession().setAttribute("deliveryPrice", deliveryPrice);

        // refer to MAIN_PAGE
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }

    private void configurationSaveOrUpdate(String configurationValue, String configurationKey) {
        // check exist configuration in Db
        Configuration configuration = configurationService.get(configurationKey);
        if (configuration == null) {
            // save configuration in DB
            configuration = new Configuration();
            configuration.setId(configurationKey);
            configuration.setValue(configurationValue);
            configuration = configurationService.add(configuration);
        } else {
            // update configuration in Db
            configuration.setValue(configurationValue);
            configurationService.update(configuration);
        }
    }
}
