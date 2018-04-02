package services.impl;

import entities.CoffeeType;
import entities.enums.DisabledFlag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.ICoffeeTypeService;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("/testContext-services.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CoffeeTypeServiceTest {
    @Autowired
    private ICoffeeTypeService coffeeTypeService;

    @Test
    public void getAllForDisabledFlag() {
        CoffeeType newOneForSave = new CoffeeType();
        newOneForSave.setTypeName("Very fragrant coffee TEST");
        newOneForSave.setPrice(1.0);
        newOneForSave.setDisabledFlag(DisabledFlag.Y);

        CoffeeType anotherOneForSave = new CoffeeType();
        anotherOneForSave.setTypeName("Black coffee with cream TEST");
        anotherOneForSave.setPrice(3.0);
        anotherOneForSave.setDisabledFlag(DisabledFlag.N);

        // check getAllForDisabledFlag before save CoffeeType in DB
        List<CoffeeType> list1 = coffeeTypeService.getAllForDisabledFlag(DisabledFlag.Y);
        List<CoffeeType> listAll1 = coffeeTypeService.getAll();

        // save CoffeeType in DB where DisabledFlag.N & DisabledFlag.Y
        CoffeeType savedWhereFlagY = coffeeTypeService.add(newOneForSave);
        assertNotNull(savedWhereFlagY);
        CoffeeType savedWhereFlagN = coffeeTypeService.add(anotherOneForSave);
        assertNotNull(savedWhereFlagN);

        // getAllForDisabledFlag check after save CoffeeType in DB
        List<CoffeeType> list2 = coffeeTypeService.getAllForDisabledFlag(DisabledFlag.Y);
        assertEquals(1, list2.size() - list1.size());
        List<CoffeeType> listAll2 = coffeeTypeService.getAll();
        assertEquals(2, listAll2.size() - listAll1.size());

        // delete saved entities
        coffeeTypeService.delete(savedWhereFlagY.getId());
        CoffeeType getIt = coffeeTypeService.get(savedWhereFlagY.getId());
        assertNull(getIt);

        coffeeTypeService.delete(savedWhereFlagN.getId());
        getIt = coffeeTypeService.get(savedWhereFlagN.getId());
        assertNull(getIt);
    }
}
