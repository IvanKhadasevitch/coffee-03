package dao.impl;

import dao.IConfigurationDao;
import entities.Configuration;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationDao extends BaseDao<Configuration> implements IConfigurationDao {
    private static Logger log = Logger.getLogger(ConfigurationDao.class);

    public ConfigurationDao() {
        super();
        clazz = Configuration.class;
    }
}
