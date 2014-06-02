package ru.arsenalpay.api.command.impl;

import org.apache.commons.lang.ObjectUtils;
import ru.arsenalpay.api.command.ApiCommand;
import ru.arsenalpay.api.command.ApiCommandProducer;
import ru.arsenalpay.api.enums.HttpMethod;
import ru.arsenalpay.api.merchant.MerchantCredentials;
import ru.arsenalpay.api.request.AbstractRequest;
import ru.arsenalpay.api.request.PaymentRequest;
import ru.arsenalpay.api.request.PaymentStatusRequest;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static ru.arsenalpay.api.util.SecurityUtils.getSignature;

/**
 * <p>The main impl of ApiCommandProducer interface</p>
 *
 * This class contains all logic of creating commands for server api
 * which are will be translated to simple requests
 *
 * @see ru.arsenalpay.api.command.ApiCommand
 * @see ru.arsenalpay.api.command.ApiCommandProducer
 *
 * @author adamether
 */
public final class ApiCommandProducerImpl implements ApiCommandProducer {

    /** ArsenalPay production server */
    private static final String SERVER_API_HOST = "https://arsenalpay.ru";

    /** Commands */
    public static final String INIT_PAY_MK = "init_pay_mk";
    public static final String INIT_PAY_MK_STATUS = "init_pay_mk_status";
    /** Feel free to add new commands here */

    /**
     * server api command name, ex "init_pay_mk"
     */
    private final String commandName;

    /**
     * Api Server request must be concrete request (witch extends AbstractRequest) specific for command
     * ex we need PaymentRequest for getting INIT_PAY_MK command
     */
    private final AbstractRequest request;
    private final MerchantCredentials credentials;


    public ApiCommandProducerImpl(String commandName, AbstractRequest request, MerchantCredentials credentials) {
        this.commandName = commandName;
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public ApiCommand getCommand() {
        // TODO: make refactoring to command design pattern
        if (INIT_PAY_MK.equalsIgnoreCase(commandName)) {
            return getInitPayMkCommand();
        } else if (INIT_PAY_MK_STATUS.equalsIgnoreCase(commandName)) {
            return getInitPayMkStatusCommand();
        } else {
            return null;
        }
    }

    /**
     * Get INIT_PAY_MK command
     * must be created with
     * @see ru.arsenalpay.api.request.PaymentRequest
     *
     * @exception IllegalStateException -- throws this exception if request is another type than
     * <b>/>PaymentRequest</b>
     *
     * @return -- ApiCommand
     */
    private ApiCommand getInitPayMkCommand() {
        if (request.getClass() != PaymentRequest.class) {
            final String message = MessageFormat.format(
                    "Request for command {0} must be {1}", INIT_PAY_MK, PaymentRequest.class
            );
            throw new IllegalStateException(message);
        }
        final PaymentRequest paymentRequest = (PaymentRequest) request;

        final String account = ObjectUtils.toString(paymentRequest.getRecipientId());
        final String phone = ObjectUtils.toString(paymentRequest.getPayerId());
        final String amount = ObjectUtils.toString(paymentRequest.getAmount());

        final String id = credentials.getId();
        final String secret = credentials.getSecret();

        Map<String, String> params = new HashMap<String, String>() {{
            put("ID", id);
            put("FUNCTION", INIT_PAY_MK);
            put("ACCOUNT", account);
            put("PHONE", phone);
            put("AMOUNT", amount);
            put("CURRENCY", "RUR");
        }};

        final String signature = getSignature(secret, id, INIT_PAY_MK, account, phone, amount);
        params.put("SIGN", signature);

        final String baseUri = MessageFormat.format("{0}/{1}/", SERVER_API_HOST, INIT_PAY_MK);

        return new ApiCommand(baseUri, params, HttpMethod.POST);
    }

    /**
     * Get INIT_PAY_MK_STATUS command
     * must be created with
     * @see ru.arsenalpay.api.request.PaymentStatusRequest
     *
     * @exception IllegalStateException -- throws this exception if request is another type than
     * <b>/>PaymentStatusRequest</b>
     *
     * @return -- ApiCommand
     */
    public ApiCommand getInitPayMkStatusCommand() {
        if (request.getClass() != PaymentStatusRequest.class) {
            final String message = MessageFormat.format(
                    "Request for command {0} must be {1}", INIT_PAY_MK_STATUS, PaymentStatusRequest.class
            );
            throw new IllegalStateException(message);
        }
        final PaymentStatusRequest statusRequest = (PaymentStatusRequest) request;

        final String rrn = ObjectUtils.toString(statusRequest.getTransactionId());

        final String id = credentials.getId();
        final String secret = credentials.getSecret();

        Map<String, String> params = new HashMap<String, String>() {{
            put("ID", id);
            put("FUNCTION", INIT_PAY_MK_STATUS);
            put("RRN", rrn);
        }};

        final String signature = getSignature(secret, id, INIT_PAY_MK_STATUS, rrn);
        params.put("SIGN", signature);

        final String baseUri = MessageFormat.format("{0}/{1}/", SERVER_API_HOST, INIT_PAY_MK);

        return new ApiCommand(baseUri, params, HttpMethod.POST);
    }

}
