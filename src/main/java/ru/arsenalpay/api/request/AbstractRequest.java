package ru.arsenalpay.api.request;

/**
 * <p>AbstractRequest for all api commands.</p>
 *
 * <p>This class will contain common for different requests data and methods in future.
 * For different commands we need different requests.
 * For example for for mobile commerce we need payment request with mobile params.</p>
 *
 * <p>Feel free to add new requests witch will extend this class.</p>
 *
 * @author adamether
 *
 */
public abstract class AbstractRequest {

    protected AbstractRequest() {
    }

}
