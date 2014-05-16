package ru.arsenalpay.api.response;

import org.simpleframework.xml.Element;

/**
 * <p>AbstractResponse for all api commands.</p>
 *
 * <p>For different commands we need different responses.
 * For example for for mobile commerce we will return {@link ru.arsenalpay.api.response.PaymentResponse} object</p>
 *
 * <p>Feel free to add new responses witch will extend this class.</p>
 *
 * @author adamether
 */
public abstract class AbstractResponse {

    /**
     * Payment transaction id or
     * RRN -- Acquirer Retrieval Reference Number
     * Required field
     */
    @Element(name = "rrn")
    protected final Long transactionId;

    /**
     * who was the payer?
     */
    @Element(name = "phone", required = false)
    protected final Long payerId;

    /**
     * who was accept the payment in merchant application?
     */
    @Element(name = "account", required = false)
    protected final Long recipientId;

    /**
     * what was the amount of payment?
     */
    @Element(required = false)
    protected final Double amount;

    /**
     * Parent constructor for other non abstract classes
     * @param transactionId payment transaction id (RRN)
     * @param payerId who was the payer?
     * @param recipientId who was accept the payment in merchant application?
     * @param amount what was the amount of payment?
     */
    protected AbstractResponse(Long transactionId, Long payerId, Long recipientId, Double amount) {
        this.transactionId = transactionId;
        this.payerId = payerId;
        this.recipientId = recipientId;
        this.amount = amount;
    }

    /**
     * Return payment transaction id (RRN)
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * Return who was the payer?
     */
    public Long getPayerId() {
        return payerId;
    }

    /**
     * Return payment transaction id (RRN)
     */

    public Long getRecipientId() {
        return recipientId;
    }

    /**
     * Return what was the amount of payment?
     */
    public Double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "AbstractResponse{" +
                "transactionId=" + transactionId +
                ", payerId=" + payerId +
                ", recipientId=" + recipientId +
                ", amount=" + amount +
                '}';
    }

}
