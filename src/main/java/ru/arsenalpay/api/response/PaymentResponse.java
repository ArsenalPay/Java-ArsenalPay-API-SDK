package ru.arsenalpay.api.response;

import com.thoughtworks.xstream.XStream;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.arsenalpay.api.exception.ArsenalPayApiException;
import ru.arsenalpay.api.exception.InternalApiException;
import ru.arsenalpay.api.exception.PaymentException;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>PaymentResponse is model of successful api server response.</p>
 *
 * @see ru.arsenalpay.api.facade.ApiCommandsFacade
 *
 * @author adamether
 *
 */
@Root(strict = false)
public final class PaymentResponse extends AbstractResponse {

    /**
     * Original response status of ArsenalMedia Server Api, see more in API protocol
     */
    @Element(name = "status")
    private final String message;

    public PaymentResponse(
        @Element(name = "rrn") Long transactionId,
        @Element(name = "phone") Long payerId,
        @Element(name = "account") Long recipientId,
        @Element(name = "amount") Double amount,
        @Element(name = "status") String message) {

        super(transactionId, payerId, recipientId, amount);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Deserialize xml object to of {@link ru.arsenalpay.api.response.PaymentResponse} type
     * @param xml server api response
     * @return object to of {@link ru.arsenalpay.api.response.PaymentResponse} type
     * @throws ArsenalPayApiException
     */
    public static PaymentResponse fromXml(String xml) throws ArsenalPayApiException {
        XStream xstream = new XStream();
        /**
         *  Using ignoreUnknownElements for tags which are not implemented yet
         *  or has been removed and you are dealing with old xml
         */
        xstream.ignoreUnknownElements();

        /**
         * Using aliasing for mapping xml fields to fields of java pojo
         * and main container as PaymentResponse class
         */
        xstream.alias("main", PaymentResponse.class);
        xstream.aliasField("rrn", PaymentResponse.class, "transactionId");
        xstream.aliasField("account", PaymentResponse.class, "recipientId");
        xstream.aliasField("phone", PaymentResponse.class, "payerId");
        xstream.aliasField("status", PaymentResponse.class, "message");

        // parse xml
        final PaymentResponse paymentResponse = (PaymentResponse) xstream.fromXML(xml);

        final String status = paymentResponse.getMessage();
        // check status -> throw exception on error
        if (! "OK".equalsIgnoreCase(status)) {
            throw translateToException(status);
        }
        return paymentResponse;
    }

    /**
     * Translate error server api response status to exception
     * @param status api server status message
     * @return some subtype of {@link ru.arsenalpay.api.exception.ArsenalPayApiException}
     */
    private static ArsenalPayApiException translateToException(String status) {
        final Map<String, ArsenalPayApiException> holder = new HashMap<String, ArsenalPayApiException>() {{
            put(null,             new InternalApiException("Api server status is null."));
            put("",               new InternalApiException("Api server status is empty."));
            put("ERR_AMOUNT",     new PaymentException("Invalid amount value."));
            put("ERR_SID",        new PaymentException("Invalid sid(session id) value."));
            put("ERR_PHONE",      new PaymentException("Invalid payerId or value doesn't exist."));
            put("ERR_CURRENCY",   new PaymentException("Invalid currency value or service doesn't support it."));
            put("ERR_DATEFORMAT", new PaymentException("Invalid date format."));
            put("ERROR",          new InternalApiException("Unknown api server error."));
            put("ERR_ACCESS",     new InternalApiException("Unknown api server error."));
            put("ERR_NODB",       new InternalApiException("Unknown api server error."));
        }};
        final ArsenalPayApiException exception = holder.get(status);
        if (exception == null) {
            final String comment = String.format("Unrecognized api server status [%s].", status);
            return new InternalApiException(comment);
        }
        return exception;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "message='" + message + '\'' +
                "} " + super.toString();
    }

}
