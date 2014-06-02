package ru.arsenalpay.api.util;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 * <p>LoggerManager is responsible for logger initializing and configuration.</p>
 */
public final class LoggerManager {

    public static final String LOG_LEVEL_PROPERTY_NAME = "log_level";

    /**
     * Init logger module. Only after calling this method use can use Logger facade.
     */
    public static void init() {
        loadLogConf();
    }

    /**
     * Load logger (log4j) configuration
     */
    private static void loadLogConf() {
        try {
            String configFilePath = Configuration.getProp("log4j.path", "conf/log4j.properties");
            PropertyConfigurator.configure(configFilePath);
            setLogLevel();

            Logger.debug("Loading log4j from: [%s]", configFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.warn("./log4j.properties does not exists in current dir, use default values");
        }
    }

    /**
     * <p>Config log level is case insensitive and can be one of:</p>
     * <ul>
     *     <li>ALL</li>
     *     <li>DEBUG</li>
     *     <li>INFO</li>
     *     <li>WARN</li>
     *     <li>ERROR</li>
     *     <li>FATAL</li>
     *     <li>OFF</li>
     *     <li>TRACE</li>
     * </ul>
     */
    private static void setLogLevel() {
        String logLevelProp = Configuration.getProp(LOG_LEVEL_PROPERTY_NAME, "info");

        Level confLevel = Level.toLevel(logLevelProp, Level.INFO);
        LogManager.getRootLogger().setLevel(Level.INFO);

        Logger.debug("Log level is set to [%s]", confLevel);
        LogManager.getRootLogger().setLevel(confLevel);
    }

}
