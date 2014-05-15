package ru.arsenalpay.api.request;

/**
 * <p>AbstractRequest for all api commands.</p>
 *
 * <p>For different commands we need different requests.
 * For example for for mobile commerce we need payment request with mobile params.</p>
 *
 * <p>Feel free to add new requests witch will extend this class.</p>
 *
 * @author adamether
 *
 */
public abstract class AbstractRequest {

    protected final Long merchantId;

    protected AbstractRequest(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

}
