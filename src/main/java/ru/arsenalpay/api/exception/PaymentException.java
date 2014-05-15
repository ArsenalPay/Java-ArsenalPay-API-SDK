package ru.arsenalpay.api.exception;

/**
 * <p>Expected, predictable payment exception.</p>
 *
 * <span>This exception is a part of contact.</span>
 *
 * <span>Use cases are:</span>
 * <ul>
 *     <li>insufficient money in the account,</li>
 *     <li>nonexistent account or account,</li>
 *     <li>unsupported currency</li>
 *     <li>any logic errors during processing of payment,</li>
 *     <li>any other incorrect payment details</li>
 * </ul>
 *
 * @author adamether
 */
public class PaymentException extends ArsenalPayApiException {

    public PaymentException(String message) {
        super(message);
    }

}
