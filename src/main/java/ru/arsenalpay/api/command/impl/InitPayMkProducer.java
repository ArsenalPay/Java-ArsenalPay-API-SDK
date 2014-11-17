package ru.arsenalpay.api.command.impl;

import org.apache.commons.lang.ObjectUtils;
import ru.arsenalpay.api.command.ApiCommand;
import ru.arsenalpay.api.command.ApiCommandProducer;
import ru.arsenalpay.api.enums.HttpMethod;
import ru.arsenalpay.api.merchant.MerchantCredentials;
import ru.arsenalpay.api.request.PaymentRequest;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static ru.arsenalpay.api.util.SecurityUtils.getSignature;

public class InitPayMkProducer implements ApiCommandProducer {

    /** ArsenalPay production server */
    private static final String SERVER_API_HOST = "https://arsenalpay.ru";

    public static final String INIT_PAY_MK = "init_pay_mk";

    private final PaymentRequest paymentRequest;
    private final MerchantCredentials credentials;

    public InitPayMkProducer(PaymentRequest paymentRequest, MerchantCredentials credentials) {
        this.paymentRequest = paymentRequest;
        this.credentials = credentials;
    }

    @Override
    public ApiCommand getCommand() {
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

}
