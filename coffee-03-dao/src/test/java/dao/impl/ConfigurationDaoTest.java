package dao.impl;

import dao.IConfigurationDao;
import entities.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("/testContext-dao.xml")
@RunWith(SpringJUnit4ClassRunner.class)

//@Transactional(transactionManager = "txManager")
public class ConfigurationDaoTest {
    @Autowired
    private JpaTransactionManager jpaTransactionManager;

    @Autowired
    private BaseDao<Configuration> baseDao;

    @Autowired
    private IConfigurationDao configurationDao;

    @Test
    public void crudAndGetAllConfigurationTest() {
        jpaTransactionManager.getDataSource();

        // get current entityManager
        EntityManager entityManager = baseDao.getEm();

        // make new Configurations
        Configuration configN = new Configuration("n");
        configN.setValue(null);

        Configuration configX = new Configuration("x");
        configX.setValue(String.valueOf(10.01));

        Configuration configM = new Configuration("m");
        configM.setValue(String.valueOf(21.03));

        // take All Configurations from DB, before any configuration was saved
        List<Configuration> configurationList1 = baseDao.getAll();

        // save Configurations in DB and persist
        Configuration isConfig = configurationDao.get(configN.getId());
        if (isConfig != null) {
            configurationDao.delete(isConfig.getId());
        }
        Configuration persistentConfigN = configurationDao.add(configN);

        assertNotNull(persistentConfigN.getId());

        //clear persistence context, make persistent(Configuration) detached
        entityManager.clear();

        // check exist in DB saved entity(configN)
        persistentConfigN = configurationDao.get(configN.getId());
        assertEquals("Configuration [n] not persist", configN, persistentConfigN);

        // update (merge entity to persistence context) entity(Configuration)
        persistentConfigN.setValue(String.valueOf(3));
        persistentConfigN = configurationDao.update(persistentConfigN);

        //clear persistence context, make persistent(Configuration) detached
        entityManager.clear();

        // check exist in DB updated entity(configN) with value = 3
        persistentConfigN = configurationDao.get(configN.getId());
        assertNotNull("Configuration [n] not persist", persistentConfigN);
        assertEquals("Configuration [n] not updated value to 3",
                String.valueOf(3), persistentConfigN.getValue());

        // save other Configurations in DB and persist
        isConfig = configurationDao.get(configX.getId());
        if (isConfig != null) {
            configurationDao.delete(isConfig.getId());
        }
        Configuration persistentConfigX = configurationDao.add(configX);
        isConfig = configurationDao.get(configM.getId());
        if (isConfig != null) {
            configurationDao.delete(isConfig.getId());
        }
        Configuration persistentConfigM = configurationDao.add(configM);

        // take All Configurations from DB, after 3 configuration was saved
        List<Configuration> configurationList2 = baseDao.getAll();

        assertNotNull(configurationList1);
        assertNotNull(configurationList2);
        assertEquals("In DB were saved not 3 Configurations: ",
                3, configurationList2.size() - configurationList1.size());

        // remove Configurations from DB
        configurationDao.delete(persistentConfigN.getId());
        configurationDao.delete(persistentConfigX.getId());
        configurationDao.delete(persistentConfigM.getId());

        // check Correctness of removal
        Configuration getIt = configurationDao.get(persistentConfigN.getId());
        assertNull("Configuration [n] after deletion is not null", getIt);

        getIt = configurationDao.get(persistentConfigX.getId());
        assertNull("Configuration [x] after deletion is not null", getIt);

        getIt = configurationDao.get(persistentConfigM.getId());
        assertNull("Configuration [m] after deletion is not null", getIt);

        //clear persistence context, make persistent(Configuration) detached
        entityManager.clear();
    }
}
