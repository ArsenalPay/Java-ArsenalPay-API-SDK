package ru.arsenalpay.api.request;

import ru.arsenalpay.api.enums.MoneySource;
import ru.arsenalpay.api.util.Configuration;

/**
 * <p>PaymentRequest is model of payment. Contains all needed data for processing payment.</p>
 *
 * <p>You should use different smart builder for producing payment type.
 * For example for producing mobile payment you must use MobileBuilder and etc</p>
 *
 * @see ru.arsenalpay.api.request.AbstractRequest
 * @see ru.arsenalpay.api.facade.ApiCommandsFacade
 * @see ru.arsenalpay.api.facade.impl.ApiCommandsFacadeImpl
 *
 * @author adamether
 */
public final class PaymentRequest extends AbstractRequest {

    /**
     * who will pay? Id in payment system ex phone number, card number and etc
     */
    private final Long payerId;

    /**
     * who will get charge in merchant application? Id in merchant system
     */
    private final Long recipientId;

    /**
     * what is the amount of charge?
     */
    private final Double amount;

    /**
     * what is the currency type? ISO-4217
     */
    private final String currency;

    /**
     * what is the source of charge?
     */
    private final MoneySource moneySource;

    /**
     * what is payment comment? (Optional) Ex "Gift from Santa."
     */
    private final String comment;

    /**
     *  is it test payment?
     */
    private final Boolean isTest;

    private PaymentRequest(MobileBuilder mobileBuilder) {
        super(Long.valueOf(Configuration.getProp("merchant.id")));
        this.moneySource = MoneySource.MOBILE;
        this.payerId = mobileBuilder.payerId;
        this.recipientId = mobileBuilder.recipientId;
        this.amount = mobileBuilder.amount;
        this.currency = mobileBuilder.currency;
        this.comment = mobileBuilder.comment;
        this.isTest = mobileBuilder.isTest;
    }

    /** Builders section for different payment types and sources */

    /**
     * Builder for mobile payments.
     */
    public static class MobileBuilder {

        private Long payerId;
        private Long recipientId;
        private Double amount;
        private String currency;
        private String comment;
        private Boolean isTest;

        /**
         * who will be pay? Id in payment system ex phone number, card number and etc
         */
        public MobileBuilder payerId(Long value) {
            this.payerId = value;
            return this;
        }

        /**
         * who will get charge in merchant application? Id in merchant system
         */
        public MobileBuilder recipientId(Long value) {
            this.recipientId = value;
            return this;
        }

        /**
         * what is the amount of charge?
         */
        public MobileBuilder amount(Double value) {
            this.amount = value;
            return this;
        }

        /**
         * what is the currency type? ISO-4217
         */
        public MobileBuilder currency(String value) {
            this.currency = value;
            return this;
        }

        /**
         * what is payment comment? (Optional) Ex "Gift from Santa."
         */
        public MobileBuilder comment(String value) {
            this.comment = value;
            return this;
        }

        /**
         *  is it test payment?
         */
        public MobileBuilder setTestMode() {
            this.isTest = true;
            return this;
        }

        public PaymentRequest build() {
            return new PaymentRequest(this);
        }

    }

    public Long getPayerId() {
        return payerId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public MoneySource getMoneySource() {
        return moneySource;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "merchantId=" + merchantId +
                ", payerId=" + payerId +
                ", recipientId=" + recipientId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", moneySource=" + moneySource +
                ", isTest=" + isTest +
                '}';
    }

}
