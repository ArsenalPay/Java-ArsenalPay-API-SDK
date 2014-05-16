package ru.arsenalpay.api.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Persister;
import ru.arsenalpay.api.enums.OperationStatus;
import ru.arsenalpay.api.exception.ArsenalPayApiRuntimeException;
import ru.arsenalpay.api.exception.InternalApiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isBlank;
import static ru.arsenalpay.api.enums.OperationStatus.*;

/**
 * <p>PaymentStatusResponse is need for getting result of check payment status api command</p>
 *
 * @see ru.arsenalpay.api.facade.ApiCommandsFacade
 *
 * @author adamether
 */
@Root(strict = false)
public final class PaymentStatusResponse extends AbstractResponse {

    private static final String SERVER_API_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * ArsenalPay status message
     */
    @Element(name = "status")
    private final String message;

    /**
     * String view of payment date
     */
    @Element(name = "datetime", required = false)
    private final String datetime;

    public PaymentStatusResponse(
            @Element(name = "rrn")      Long transactionId,
            @Element(name = "phone")    Long payerId,
            @Element(name = "account")  Long recipientId,
            @Element(name = "amount")   Double amount,
            @Element(name = "status")   String message,
            @Element(name = "datetime") String datetime) {

        super(transactionId, payerId, recipientId, amount);
        this.message = message;
        this.datetime = datetime;
    }

    /**
     * Get the date of payment
     * @return object of {@link java.util.Date}
     */
    public Date getDate() {
        if (isBlank(datetime)) {
            return null;
        }
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
    public static PaymentStatusResponse fromXml(String xml) throws InternalApiException {
        // PENDING: and what about message parsing? and throwing excseption?
        return read(xml);
    }

    /**
     * Simple read object from xml
     * And nothing else
     * @param xml server api response
     * @return object of type {@link ru.arsenalpay.api.response.PaymentStatusResponse}
     * @throws InternalApiException while deserializing process
     */
    private static PaymentStatusResponse read(String xml) throws InternalApiException {
        try {
            Persister persister = new Persister();
            return persister.read(PaymentStatusResponse.class, xml);
        } catch (Exception e) {
            throw new InternalApiException(e);
        }
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
