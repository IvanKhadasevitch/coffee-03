package command.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandType {
    ORDERS("orders/main.jsp", "Orders", "orders.title", "ordersController"),
    DELIVERY("orders/deliveryOrder.jsp", "Delivery", "orders.title", "ordersDeliveryController"),
    ORDERS_SHOW("orders/showOrder.jsp", "ShowOrder", "orders.title", "ordersShowController"),
    COFFEE("coffee/main.jsp", "Coffee", "coffee.title", "coffeeController"),
    COFFEE_CHANGE("coffee/changeCoffee.jsp", "changeCoffee", "coffee.title", "coffeeChangeController"),
    COFFEE_ADD("coffee/addCoffee.jsp", "addCoffee", "coffee.title", "coffeeAddController"),
    CONFIGURATION("configuration/main.jsp", "Configuration", "configuration.title", "configurationController");

    private String pagePath;
    private String pageName;
    private String i18nKey;
    private String controller;

    public static CommandType getByPageName(String page) {
        //del after debug
        System.out.println(String.format("Start CommandType.getByPageName page=%s ",page));

        for (CommandType type : CommandType.values()) {
            if (type.pageName.equalsIgnoreCase(page)) {

                //del after debug
                System.out.println("CommandType, return type.pagePath= " + type.pagePath);

                return type;
            }
        }
        // If found nothing, return to the start page:

        //del after debug
        System.out.println("CommandType, found nothing, return to the start page:" + ORDERS);

        return ORDERS;
    }
}
