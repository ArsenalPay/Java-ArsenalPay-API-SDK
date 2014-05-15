package ru.arsenalpay.api.response;

import com.thoughtworks.xstream.XStream;
import ru.arsenalpay.api.enums.OperationStatus;
import ru.arsenalpay.api.exception.ArsenalPayApiRuntimeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ru.arsenalpay.api.enums.OperationStatus.*;

/**
 * <p>PaymentStatusResponse is need for getting result of check payment status api command</p>
 *
 * @see ru.arsenalpay.api.facade.ApiCommandsFacade
 *
 * @author adamether
 */
public final class PaymentStatusResponse extends AbstractResponse {

    public static final String SERVER_API_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * String view of payment date
     */
    private final String datetime;

    /**
     * ArsenalPay status message
     */
    private final String message;

    public PaymentStatusResponse(Long transactionId,
                                 Long payerId,
                                 Long recipientId,
                                 Double amount,
                                 String message,
                                 String datetime) {

        super(transactionId, payerId, recipientId, amount);
        this.message = message;
        this.datetime = datetime;
    }

    /**
     * Get the date of payment
     * @return object of {@link java.util.Date}
     */
    public Date getDate() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SERVER_API_DATE_FORMAT);
            return simpleDateFormat.parse(datetime);
        } catch (ParseException e) {
            throw new ArsenalPayApiRuntimeException(e);
        }
    }

    /**
     * Get operation status {@link ru.arsenalpay.api.enums.OperationStatus}
     * @return operation status {@link ru.arsenalpay.api.enums.OperationStatus}
     */
    public OperationStatus getMessage() {
        final Map<String, OperationStatus> holder = new HashMap<String, OperationStatus>() {{
            put("NO_ANSWER", NOT_REGISTERED);
            put("OK_ANSWER", IN_PROGRESS);
            put("OK_INIT",   IN_PROGRESS);
            put("OK_PAY",    SUCCESS);
            put("ERR_PAY",   REFUSED);
        }};
        final OperationStatus operationStatus = holder.get(message);
        if (operationStatus == null) {
            final String text = String.format("Unrecognized server API status: [%s]", message);
            throw new ArsenalPayApiRuntimeException(text);
        }
        return operationStatus;
    }

    /**
     * Deserialize xml object to {@link ru.arsenalpay.api.response.PaymentStatusResponse} type
     * @param xml server api response
     * @return object of type {@link ru.arsenalpay.api.response.PaymentStatusResponse}
     */
    public static PaymentStatusResponse fromXml(String xml) {
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
        xstream.alias("main", PaymentStatusResponse.class);
        xstream.aliasField("rrn", PaymentStatusResponse.class, "transactionId");
        xstream.aliasField("account", PaymentStatusResponse.class, "recipientId");
        xstream.aliasField("phone", PaymentStatusResponse.class, "payerId");
        xstream.aliasField("status", PaymentStatusResponse.class, "message");

        final PaymentStatusResponse paymentStatusResponse = (PaymentStatusResponse) xstream.fromXML(xml);

        System.out.println("====== PSR: " + paymentStatusResponse);

        return paymentStatusResponse;
    }

    @Override
    public String toString() {
        return "PaymentStatusResponse{" +
                "date=" + getDate() +
                ", message=" + getMessage() +
                ", datetime='" + datetime + '\'' +
                "} " + super.toString();
    }

}
