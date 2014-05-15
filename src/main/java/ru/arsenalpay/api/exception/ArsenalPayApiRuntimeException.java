package ru.arsenalpay.api.exception;

/**
 * <p>Unexpected, unpredictable exception in other words runtime error.</p>
 *
 * Any runtime error should apply to this type of exception or the heir of this class.
 *
 * @author adamether
 */
public class ArsenalPayApiRuntimeException extends RuntimeException {

    public ArsenalPayApiRuntimeException() {
    }

    public ArsenalPayApiRuntimeException(String s) {
        super(s);
    }

    public ArsenalPayApiRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ArsenalPayApiRuntimeException(Throwable throwable) {
        super(throwable);
    }

}
