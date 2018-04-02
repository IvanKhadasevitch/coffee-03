package services.impl;

import entities.CoffeeOrder;
import entities.CoffeeOrderItem;
import entities.CoffeeType;
import entities.enums.DisabledFlag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.ICoffeeOrderItemService;
import services.ICoffeeOrderService;
import services.ICoffeeTypeService;
import services.IConfigurationService;
import vo.CoffeeOrderAndCost;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("/testContext-services.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CoffeeOrderItemServiceTest {
    @Autowired
    private ICoffeeTypeService coffeeTypeService;
    @Autowired
    private IConfigurationService configurationService;
    @Autowired
    private ICoffeeOrderService coffeeOrderService;
    @Autowired
    private ICoffeeOrderItemService coffeeOrderItemService;

    @Test
    public void getAllForOrderId() {
        // make CoffeeTypes & save in Db
        CoffeeType coffeeType1 = new CoffeeType();
        coffeeType1.setTypeName("Coffee1");
        coffeeType1.setPrice(3.0);
        coffeeType1.setDisabledFlag(DisabledFlag.N);
        coffeeType1 = coffeeTypeService.add(coffeeType1);
        List<CoffeeType> coffeeTypeList = new LinkedList<>();
        coffeeTypeList.add(coffeeType1);

        CoffeeType coffeeType2 = new CoffeeType();  // DisabledFlag.N by default
        coffeeType2.setTypeName("Coffee2");
        coffeeType2.setPrice(7.0);
        coffeeType2 = coffeeTypeService.add(coffeeType2);
        coffeeTypeList.add(coffeeType2);

        // make CoffeeOrderItems & add to coffeeOrderItemList
        List<CoffeeOrderItem> coffeeOrderItemList = new LinkedList<>();
        CoffeeOrderItem coffeeOrderItem = new CoffeeOrderItem();
        coffeeOrderItem.setType(coffeeTypeList.get(0));
        coffeeOrderItem.setQuantity(4);
        coffeeOrderItemList.add(coffeeOrderItem);

        coffeeOrderItem = new CoffeeOrderItem();
        coffeeOrderItem.setType(coffeeTypeList.get(1));
        coffeeOrderItem.setQuantity(5);
        coffeeOrderItemList.add(coffeeOrderItem);

        //delete configuration if exist
        if (configurationService.get("n") != null) {
            configurationService.delete("n");
        }
        if (configurationService.get("x") != null) {
            configurationService.delete("x");
        }
        if (configurationService.get("m") != null) {
            configurationService.delete("m");
        }

        // calculate cost & make CoffeeOrder for default configuration
        // every 5(n) cups is free, delivery cost is 2(m); if order sum more then 10(x) - delivery is free
        // order is 4 cups with price 3 pear cup & 5 cups with price 7 pear cup
        CoffeeOrderAndCost coffeeOrderAndCost = coffeeOrderService.makeOrder("TEST Petrov",
                "TEST Street", coffeeOrderItemList);
        assertNotNull(coffeeOrderAndCost);

        // check getAllForOrderId
        assertEquals(2,
                coffeeOrderItemService.getAllForOrderId(coffeeOrderAndCost.getCoffeeOrder().getId()).size());

        // delete CoffeeOrder & related to it CoffeeOrderItems (by cascade)
        coffeeOrderService.delete(coffeeOrderAndCost.getCoffeeOrder().getId());
        CoffeeOrder coffeeOrderFromDB = coffeeOrderService.get(coffeeOrderAndCost.getCoffeeOrder().getId());
        assertNull(coffeeOrderFromDB);
        List<CoffeeOrderItem> allOrderItemsForOrderId =
                coffeeOrderItemService.getAllForOrderId(coffeeOrderAndCost.getCoffeeOrder().getId());
        assertTrue(allOrderItemsForOrderId.isEmpty());

        // delete saved CoffeeTypes
        for (CoffeeType coffeeType : coffeeTypeList) {
            coffeeTypeService.delete(coffeeType.getId());
            CoffeeType coffeeTypeFromDB = coffeeTypeService.get(coffeeType.getId());
            assertNull(coffeeTypeFromDB);
        }
    }
}
