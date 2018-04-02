package dao.impl;

import dao.ICoffeeTypeDao;
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
public class CoffeeTypeDaoTest {
    @Autowired
    private ICoffeeTypeDao coffeeTypeDao;

    @Test
    @Rollback(value = true)
    public void getAllForDisabledFlag() {
        // make new CoffeeType
        CoffeeType coffeeType = new CoffeeType("CoffeeType TEST coffee", 12.21,
                DisabledFlag.Y);
        // take all CoffeeType from DB with DisabledFlag.Y
        List<CoffeeType> coffeeTypeList1 = coffeeTypeDao.getAllForDisabledFlag(DisabledFlag.Y);

        // safe CoffeeType in DB
        CoffeeType coffeeTypeSaved = coffeeTypeDao.add(coffeeType);

        // take again CoffeeType from DB with DisabledFlag.Y
        List<CoffeeType> coffeeTypeList2 = coffeeTypeDao.getAllForDisabledFlag(DisabledFlag.Y);
        assertEquals("Add not one CoffeeType with DisabledFlag.Y",
                1, coffeeTypeList2.size() - coffeeTypeList1.size());

        // remove saved CoffeeType
        coffeeTypeDao.delete(coffeeTypeSaved.getId());

        // check Correctness of removal
        CoffeeType coffeeTypeGot = coffeeTypeDao.get(coffeeTypeSaved.getId());
        assertNull("CoffeeType wasn't deleted from DB", coffeeTypeGot);
    }
}
