package ru.arsenalpay.api.exception;

/**
 * <p>Abstract ArsenalPayApi.<p/>
 *
 * Use in this project code only heirs of this class:
 * @see InternalApiException
 * @see PaymentException
 * @see ArsenalPayApiRuntimeException
 * @see ConfigurationLoadingException
 * @see HttpClientInitializingException
 *
 * @author adamether
 */
public abstract class ArsenalPayApiException extends Exception {

    protected ArsenalPayApiException() {
    }

    protected ArsenalPayApiException(String s) {
        super(s);
    }

    protected ArsenalPayApiException(String s, Throwable throwable) {
        super(s, throwable);
    }

    protected ArsenalPayApiException(Throwable throwable) {
        super(throwable);
    }

}
