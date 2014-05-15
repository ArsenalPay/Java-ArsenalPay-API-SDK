package ru.arsenalpay.api.exception;

/**
 *  <p>Error occurs during the loading and reading configuration.<p/>
 *
 *  @see ru.arsenalpay.api.util.Configuration
 *
 *  @author adamether
 */
public class ConfigurationLoadingException extends ArsenalPayApiRuntimeException {

    public ConfigurationLoadingException() {
    }

    public ConfigurationLoadingException(Throwable throwable) {
        super(throwable);
    }

    public ConfigurationLoadingException(String s) {
    }

}
