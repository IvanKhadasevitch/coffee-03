package dao.impl;

import dao.ICoffeeOrderDao;
import dao.ICoffeeOrderItemDao;
import dao.ICoffeeTypeDao;
import entities.CoffeeOrder;
import entities.CoffeeOrderItem;
import entities.CoffeeType;
import entities.enums.DisabledFlag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("/testContext-dao.xml")
@RunWith(SpringJUnit4ClassRunner.class)

@Transactional(transactionManager = "txManager")
public class CoffeeOrderItemDaoTest {

    @Autowired
    private ICoffeeOrderItemDao coffeeOrderItemDao;
    @Autowired
    private ICoffeeOrderDao coffeeOrderDao;
    @Autowired
    private ICoffeeTypeDao coffeeTypeDao;

    @Test
    @Rollback(value = true)
    public void getAllForOrderId() {
        // make new CoffeeOrderItem
        CoffeeOrderItem coffeeOrderItem = new CoffeeOrderItem(3);

        // make new CoffeeType
        CoffeeType coffeeType = new CoffeeType("Wary hot TEST coffee", 21.01,
                DisabledFlag.Y);

        // make new CoffeeOrder
        CoffeeOrder coffeeOrder = new CoffeeOrder("Sunny street TEST");
        coffeeOrder.addCoffeeOrderItem(coffeeOrderItem);

        // safe CoffeeType in DB
        CoffeeType coffeeTypeSaved = coffeeTypeDao.add(coffeeType);

        // add CoffeeType to the OrderItem
        coffeeOrderItem.setType(coffeeTypeSaved);

        // safe CoffeeOrder in DB, CoffeeOrderItem will be saved by cascade
        CoffeeOrder coffeeOrderSaved = coffeeOrderDao.add(coffeeOrder);

        // take all CoffeeOrderItems from DB for definite Order
        List<CoffeeOrderItem> coffeeOrderItemList = coffeeOrderItemDao
                .getAllForOrderId(coffeeOrderSaved.getId());
        assertFalse("CoffeeOrderItemList is empty", coffeeOrderItemList.isEmpty());
        assertEquals("CoffeeOrderItemList has not one element",
                1, coffeeOrderItemList.size());

        // remove saved CoffeeOrder, coffeeOrderItem will be removed by cascade
        coffeeOrderDao.delete(coffeeOrderSaved.getId());

        // check Correctness of removal
        CoffeeOrder coffeeOrderGot = coffeeOrderDao.get(coffeeOrderSaved.getId());
        assertNull("CoffeeOrder wasn't deleted from DB", coffeeOrderGot);
        CoffeeOrderItem coffeeOrderItemGot = coffeeOrderItemDao
                .get(coffeeOrderSaved.getCoffeeOrderItemList().get(0).getId());
        assertNull("CoffeeOrderItem wasn't deleted from DB", coffeeOrderItemGot);

        // remove saved CoffeeType
        coffeeTypeDao.delete(coffeeTypeSaved.getId());

        // check Correctness of removal
        CoffeeType coffeeTypeGot = coffeeTypeDao.get(coffeeTypeSaved.getId());
        assertNull("CoffeeType wasn't deleted from DB", coffeeTypeGot);
    }
}
