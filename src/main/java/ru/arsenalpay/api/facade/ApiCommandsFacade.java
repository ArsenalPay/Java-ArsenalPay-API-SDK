package ru.arsenalpay.api.facade;

import ru.arsenalpay.api.exception.ArsenalPayApiException;
import ru.arsenalpay.api.exception.InternalApiException;
import ru.arsenalpay.api.request.PaymentRequest;
import ru.arsenalpay.api.request.PaymentStatusRequest;
import ru.arsenalpay.api.response.PaymentResponse;
import ru.arsenalpay.api.response.PaymentStatusResponse;

/**
 * <p>ArsenalPay Server API facade for executing commands toward merchant.</p>
 *
 * @see ru.arsenalpay.api.facade.impl.ApiCommandsFacadeImpl
 *
 * @author adamether
 */
public interface ApiCommandsFacade {

    /**
     * <p>Make a request for processing a payment toward merchant account</p>
     *
     * <b>This method is a composition of two parts:</b>
     * <ul>
     *     <li>payment request validation (checking account)</li>
     *     <li>process payment</li>
     * </ul>
     *
     * <p>
     *     <b>Take a note!</b>
     *     <span>
     *         Upon successful completion of this request you should first find out the status of payments
     *         and make sure that it has successfully performed using the method <b>checkPaymentStatus</b>
     *         (the default option) or we will send you a callback if you want
     *         then and only then charge money to account through the billing system of your application.
     *     </span>
     * </p>
     *
     * <b>Mobile payment:</b>
     * <p>After calling this method your client ('payerId' in request payment as phone number and payment source)
     * will get sms message and then if client send sms with confirmation code transfer actually be held.
     * User must confirm payment within a half-hour (30 minutes). In other cases payment will be refused.</p>
     *
     * @param request the request for payment {@link ru.arsenalpay.api.request.PaymentRequest}.
     * Assumed that you reliably get data for payment through your own application forms.
     * @return you will get {@link ru.arsenalpay.api.response.PaymentResponse} object in case if payment request
     * was successful processed or exception will be thrown:
     * @throws ru.arsenalpay.api.exception.PaymentException
     *                            if payment request has invalid fields or something wrong with payment logic
     * @throws ru.arsenalpay.api.exception.InternalApiException
     *                            if ArsenalPay server api is unavailable, I/O defects or other system situations
     */
    PaymentResponse requestPayment(PaymentRequest request) throws ArsenalPayApiException;

    /**
     * <p>Make a request for checking of payment status.</p>
     *
     * @param request -- the payment status request
     * @return paymentStatusResponse -- some of status value
     * @see ru.arsenalpay.api.enums.OperationStatus
     * @throws InternalApiException
     */
    PaymentStatusResponse checkPaymentStatus(PaymentStatusRequest request) throws InternalApiException;

}
