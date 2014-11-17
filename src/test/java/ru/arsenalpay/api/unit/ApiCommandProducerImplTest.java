package ru.arsenalpay.api.unit;

import org.junit.Before;
import org.junit.Test;
import ru.arsenalpay.api.command.ApiCommand;
import ru.arsenalpay.api.command.ApiCommandProducer;
import ru.arsenalpay.api.command.impl.InitPayMkProducer;
import ru.arsenalpay.api.command.impl.InitPayMkStatusProducer;
import ru.arsenalpay.api.merchant.MerchantCredentials;
import ru.arsenalpay.api.request.PaymentRequest;
import ru.arsenalpay.api.request.PaymentStatusRequest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.arsenalpay.api.enums.HttpMethod.POST;

public class ApiCommandProducerImplTest {

    private PaymentRequest paymentRequest;
    private PaymentStatusRequest paymentStatusRequest;

    @Before
    public void setUp() throws Exception {
        paymentRequest = new PaymentRequest.MobileBuilder()
                .payerId(9140001111L)
                .recipientId(123456789L)
                .amount(1.25D)
                .currency("RUR")
                .build();

        paymentStatusRequest = new PaymentStatusRequest(123456L);
    }

    @Test
    public void testProduceInitPayMkCommand() throws Exception {
        final MerchantCredentials credentials = new MerchantCredentials("2096", "123456");
        ApiCommandProducer producer = new InitPayMkProducer(paymentRequest, credentials);
        final ApiCommand apiCommand = producer.getCommand();

        assertNotNull(apiCommand);
        assertEquals(POST, apiCommand.getHttpMethod());
        assertEquals("https://arsenalpay.ru/init_pay_mk/", apiCommand.getBaseUri());

        final Map<String, String> expectedParams = new LinkedHashMap<String, String>() {{
            put("SIGN", "1e304e4920f14ea2ada67e5db2e39b1d");
            put("PHONE", "9140001111");
            put("FUNCTION", "init_pay_mk");
            put("CURRENCY", "RUR");
            put("ID", "2096");
            put("AMOUNT", "1.25");
            put("ACCOUNT", "123456789");
        }};
        assertEquals(expectedParams, apiCommand.getParams());

        final String expectedFullUri =
                "https://arsenalpay.ru/init_pay_mk/" +
                        "?SIGN=1e304e4920f14ea2ada67e5db2e39b1d" +
                        "&PHONE=9140001111" +
                        "&FUNCTION=init_pay_mk" +
                        "&CURRENCY=RUR" +
                        "&ID=2096" +
                        "&AMOUNT=1.25" +
                        "&ACCOUNT=123456789";
        assertEquals(expectedFullUri, apiCommand.getFullUri());
    }

    @Test
    public void testProduceInitPayMkStatus() throws Exception {
        final MerchantCredentials credentials = new MerchantCredentials("2096", "123456");
        ApiCommandProducer producer = new InitPayMkStatusProducer(paymentStatusRequest, credentials);
        final ApiCommand apiCommand = producer.getCommand();

        assertNotNull(apiCommand);
        assertEquals(POST, apiCommand.getHttpMethod());
        assertEquals("https://arsenalpay.ru/init_pay_mk/", apiCommand.getBaseUri());

        final Map<String, String> expectedParams = new HashMap<String, String>() {{
            put("SIGN", "388149ef1331aaf88910db84737784f0");
            put("FUNCTION", "init_pay_mk_status");
            put("ID", "2096");
            put("RRN", "123456");
        }};
        assertEquals(expectedParams, apiCommand.getParams());
        final String expectedFullUri =
                "https://arsenalpay.ru/init_pay_mk/" +
                        "?SIGN=388149ef1331aaf88910db84737784f0" +
                        "&RRN=123456" +
                        "&FUNCTION=init_pay_mk_status" +
                        "&ID=2096";

        assertEquals(expectedFullUri, apiCommand.getFullUri());
    }

}
