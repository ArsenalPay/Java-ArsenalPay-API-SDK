package ru.arsenalpay.api.util;

import ru.arsenalpay.api.exception.ConfigurationLoadingException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * <p>SDK configuration class. Simple extending of {@link java.util.Properties}</p>
 *
 * There are two modes 'production' and 'test'. Production by default.
 *
 * @author adamether
 */
public final class Configuration extends Properties {

    private Configuration(String pathToProperty) {
        read(pathToProperty);
    }

    private static final class InstanceHolder {
        private static final Configuration INSTANCE = new Configuration(
                "src/main/java/ru/arsenalpay/api/conf/sdk.properties"
        );
    }

    private static final class TestInstanceHolder {
        private static final Configuration INSTANCE = new Configuration(
                "src/main/java/ru/arsenalpay/api/conf/test-sdk.properties"
        );
    }

    private static Configuration getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static Configuration getTestInstance() {
        return TestInstanceHolder.INSTANCE;
    }

    /**
     * Read sdk.properties configuration for production / test env
     * @param pathToProperty -- path to file with properties
     */
    public void read(String pathToProperty) {
        try {
            load(new FileReader(pathToProperty));
        } catch (IOException e) {
            throw new ConfigurationLoadingException();
        }
    }

    /**
     * Get property value by passed key
     * @param  key   -- property name
     * @return value -- property value
     */
    public static String getProp(String key) {
        return getInstance().getProperty(key);
    }

    /**
     * Get property value by passed key
     * if value doesn't exist return defaultValue
     * @param  key                   -- property name
     * @param  defaultValue          -- defaultValue
     * @return value or defaultValue -- property value
     */
    public static String getProp(String key, String defaultValue) {
        return getInstance().getProperty(key, defaultValue);
    }

}
