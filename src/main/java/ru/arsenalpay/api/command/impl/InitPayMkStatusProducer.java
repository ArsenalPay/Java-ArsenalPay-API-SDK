package ru.arsenalpay.api.command.impl;

import org.apache.commons.lang.ObjectUtils;
import ru.arsenalpay.api.command.ApiCommand;
import ru.arsenalpay.api.command.ApiCommandProducer;
import ru.arsenalpay.api.enums.HttpMethod;
import ru.arsenalpay.api.merchant.MerchantCredentials;
import ru.arsenalpay.api.request.PaymentStatusRequest;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static ru.arsenalpay.api.util.SecurityUtils.getSignature;

public class InitPayMkStatusProducer implements ApiCommandProducer {

    /** ArsenalPay production server */
    private static final String SERVER_API_HOST = "https://arsenalpay.ru";

    public static final String INIT_PAY_MK = "init_pay_mk";
    public static final String INIT_PAY_MK_STATUS = "init_pay_mk_status";

    private final PaymentStatusRequest statusRequest;
    private final MerchantCredentials credentials;

    public InitPayMkStatusProducer(PaymentStatusRequest paymentRequest, MerchantCredentials credentials) {
        this.statusRequest = paymentRequest;
        this.credentials = credentials;
    }

    @Override
    public ApiCommand getCommand() {
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
