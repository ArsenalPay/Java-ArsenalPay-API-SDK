package ru.arsenalpay.api.exception;

/**
 * <p>Expected, predictable internal api exception.</p>
 *
 * Description: any system error whether transportation, processing or even nuclear war
 * must be wrapped in this type of exception.
 *
 * @author adamether
 */
public class InternalApiException extends ArsenalPayApiException {

    public InternalApiException(Throwable throwable) {
        super(throwable);
    }

    public InternalApiException(String message) {
        super(message);
    }

}
