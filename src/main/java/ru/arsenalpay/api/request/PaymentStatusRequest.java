package ru.arsenalpay.api.request;

/**
 * <p>PaymentStatusRequest is need for check payment status api command.</p>
 *
 * @see ru.arsenalpay.api.facade.ApiCommandsFacade
 *
 * @author adamether
 */
public final class PaymentStatusRequest extends AbstractRequest {

    /**
     * payment transaction id (RRN)
     */
    private final Long transactionId;

    public PaymentStatusRequest(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

}
