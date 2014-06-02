package ru.arsenalpay.api.util;

import org.apache.log4j.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Logger facade for logging.</p>
 */
public class Logger {

    public static boolean isTraceEnabled() {
        return org.apache.log4j.Logger.getLogger(getCallerClassName()).isTraceEnabled();
    }

    public static boolean isDebugEnabled() {
        return org.apache.log4j.Logger.getLogger(getCallerClassName()).isDebugEnabled();
    }

    public static void trace(String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).trace(format(message, args));
    }

    public static void debug(String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).debug(format(message, args));
    }

    public static void debug(Throwable e, String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).debug(format(message, args), e);
    }

    public static void info(String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).info(format(message, args));
    }

    public static void info(Throwable e, String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).info(format(message, args), e);
    }

    public static void warn(String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).warn(format(message, args));
    }

    public static void warn(Throwable e, String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).warn(format(message, args), e);
    }

    public static void error(String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).error(format(message, args));
    }

    public static void error(Throwable e, String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).error(format(message, args), e);
    }

    public static void fatal(String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).fatal(format(message, args));
    }

    public static void fatal(Throwable e, String message, Object... args) {
        org.apache.log4j.Logger.getLogger(getCallerClassName()).fatal(format(message, args), e);
    }

    static String getCallerClassName() {
        final int level = 5;
        return getCallerClassName(level);
    }

    static String getCallerClassName(final int level) {
        CallInfo ci = getCallerInformations(level);
        return ci.className;
    }

    /**
     * Examine stack trace to get caller
     *
     * @param level method stack depth
     * @return who called the logger
     */
    static CallInfo getCallerInformations(int level) {
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
        StackTraceElement caller = callStack[level];
        return new CallInfo(caller.getClassName(), caller.getMethodName());
    }

    static class CallInfo {

        public String className;
        public String methodName;

        public CallInfo() {}

        public CallInfo(String className, String methodName) {
            this.className = className;
            this.methodName = methodName;
        }

    }

    /**
     * Try to format messages using java Formatter.
     * Fall back to the plain message if error.
     */
    static String format(String msg, Object... args) {
        try {
            if (args != null && args.length > 0) {
                return String.format(msg, args);
            }
            return msg;
        } catch (Exception e) {
            return msg;
        }
    }

    public static int getLogLevel() {
        for (Map.Entry<Integer, Integer> entry : logLevelEquivalents.entrySet()) {
            if (entry.getValue().equals(org.apache.log4j.Logger.getRootLogger().getLevel().toInt())) {
                return entry.getKey();
            }
        }
        return 0;
    }

    public static void setLogLevel(int logLevel) {
        logLevel = logLevelEquivalents.get(logLevel) == null ? 4 : logLevelEquivalents.get(logLevel);
        org.apache.log4j.Logger.getRootLogger().setLevel(Level.toLevel(logLevel));
    }

    static Map<Integer, Integer> logLevelEquivalents = new HashMap<Integer, Integer>() {{
        put(0, 50000); //FATAL
        put(1, 40000); //ERROR
        put(2, 30000); //WARN
        put(3, 20000); //INFO
        put(4, 10000); //DEBUG
    }};

}
