package services.impl;

import dao.IConfigurationDao;
import entities.Configuration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.IConfigurationService;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(transactionManager = "txManager")
public class ConfigurationService
        extends BaseService<Configuration> implements IConfigurationService {
    private static Logger log = Logger.getLogger(ConfigurationService.class);

    private IConfigurationDao configurationDao;

    private final Map<String,String> DEFAULT_CONFIGURATION = new HashMap<>();
    {
        DEFAULT_CONFIGURATION.put("n","5");
        DEFAULT_CONFIGURATION.put("x","10");
        DEFAULT_CONFIGURATION.put("m","2");
    }

    @Autowired
    public ConfigurationService(IConfigurationDao configurationDao) {
        super();
        this.configurationDao = configurationDao;
    }

    /**
     * Returns the configuration value for the key specified by the parameter idKey
     * from the database. If there is no configuration in the database,
     * returns the default configuration for the specified key.
     *
     * @param idKey defines a key in the database to find the configuration
     * @return the configuration value for the key specified by the parameter idKey
     * from the database. If there is no configuration in the database,
     * returns the default configuration for the specified key, or null
     * if not in the database and there is no default configuration
     */
    @Override
    public String getValue(String idKey) {
        String configurationValue = null;
        Configuration configuration = configurationDao.get(idKey);
        if (configuration == null) {
            configurationValue = DEFAULT_CONFIGURATION.get(idKey);
        } else {
            configurationValue = configuration.getValue();
        }
        // "n" configuration can't be zero
        if ("n".equals(idKey) && Integer.valueOf(configurationValue) == 0) {
            configurationValue = DEFAULT_CONFIGURATION.get(idKey);
        }

        return configurationValue;
    }
}
