package services;

import entities.Configuration;

public interface IConfigurationService extends IService<Configuration> {
    /**
     * Returns the configuration value for the key specified by the parameter idKey
     * from the database. If there is no configuration in the database,
     * returns the default configuration for the specified key.
     *
     * @param idKey defines a key in the database to find the configuration
     * @return the configuration value for the key specified by the parameter idKey
     *         from the database. If there is no configuration in the database,
     *         returns the default configuration for the specified key, or null
     *         if not in the database and there is no default configuration
     */
    String getValue(String idKey);
}
