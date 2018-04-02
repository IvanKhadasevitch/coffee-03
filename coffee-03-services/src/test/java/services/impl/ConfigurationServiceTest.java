package services.impl;

import entities.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.IConfigurationService;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("/testContext-services.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfigurationServiceTest {
    @Autowired
    private IConfigurationService configurationService;

    @Test
    public void crudAndGetAllTest() {
        // make new Configurations
        Configuration configN = new Configuration("n");
        configN.setValue(null);

        Configuration configX = new Configuration("x");
        configX.setValue(String.valueOf(10.01));

        Configuration configM = new Configuration("m");
        configM.setValue(String.valueOf(21.03));

        // take All Configurations from DB, before any configuration was saved
        List<Configuration> configurationList1 = configurationService.getAll();

        // save Configurations in DB and persist
        Configuration isConfig = configurationService.get(configN.getId());
        if (isConfig != null) {
            configurationService.delete(isConfig.getId());
        }
        Configuration persistentConfigN = configurationService.add(configN);

        assertNotNull(persistentConfigN.getId());

        // check exist in DB saved entity(configN)
        persistentConfigN = configurationService.get(configN.getId());
        assertEquals("Configuration [n] not persist", configN, persistentConfigN);

        // update (merge entity to persistence context) entity(Configuration)
        persistentConfigN.setValue(String.valueOf(3));
        persistentConfigN = configurationService.update(persistentConfigN);

        // check exist in DB updated entity(configN) with value = 3
        persistentConfigN = configurationService.get(configN.getId());
        assertNotNull("Configuration [n] not persist", persistentConfigN);
        assertEquals("Configuration [n] not updated value to 3",
                String.valueOf(3), persistentConfigN.getValue());

        // save other Configurations in DB and persist
        isConfig = configurationService.get(configX.getId());
        if (isConfig != null) {
            configurationService.delete(isConfig.getId());
        }
        // check default value of "x" configuration
        assertEquals(10.0, Double.valueOf(configurationService.getValue("x")),
                0.00001);
        Configuration persistentConfigX = configurationService.add(configX);

        isConfig = configurationService.get(configM.getId());
        if (isConfig != null) {
            configurationService.delete(isConfig.getId());
        }
        // check default value of "m" configuration
        assertEquals(2.0, Double.valueOf(configurationService.getValue("m")),
                0.00001);
        Configuration persistentConfigM = configurationService.add(configM);

        // take All Configurations from DB, after 3 configuration was saved
        List<Configuration> configurationList2 = configurationService.getAll();

        assertNotNull(configurationList1);
        assertNotNull(configurationList2);
        assertEquals("In DB were saved not 3 Configurations: ",
                3, configurationList2.size() - configurationList1.size());

        // remove Configurations from DB
        configurationService.delete(persistentConfigN.getId());
        configurationService.delete(persistentConfigX.getId());
        configurationService.delete(persistentConfigM.getId());

        // check Correctness of removal
        Configuration getIt = configurationService.get(persistentConfigN.getId());
        assertNull("Configuration [n] after deletion is not null", getIt);

        getIt = configurationService.get(persistentConfigX.getId());
        assertNull("Configuration [x] after deletion is not null", getIt);

        getIt = configurationService.get(persistentConfigM.getId());
        assertNull("Configuration [m] after deletion is not null", getIt);
    }

    @Test
    public void getValue() {
        // null test for not exist configuration
        assertNull(configurationService.getValue("not exist configuration"));

        //delete configuration if exist
        if (configurationService.get("n") != null) {
            configurationService.delete("n");
        }
        // check default value
        assertEquals(5.0, Double.valueOf(configurationService.getValue("n")),
                0.00001);

        // make & save configuration in DB
        // "n" configuration can't be zero - check
        Configuration configuration = new Configuration();
        configuration.setId("n");
        configuration.setValue("0");
        configuration = configurationService.add(configuration);
        assertEquals(5.0, Double.valueOf(configurationService.getValue("n")),
                0.00001);

        // update configuration in Db
        configuration.setValue("15");
        configurationService.update(configuration);
        assertEquals(15.0, Double.valueOf(configurationService.getValue("n")),
                0.00001);

        // remove saved configuration from DB
        configurationService.delete(configuration.getId());
        // check Correctness of removal
        Configuration configurationFromDB = configurationService.get(configuration.getId());
        assertNull("Configuration [n] after deletion is not null",configurationFromDB);
    }
}
