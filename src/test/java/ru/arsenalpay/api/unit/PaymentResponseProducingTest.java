package ru.arsenalpay.api.unit;


import org.apache.commons.io.FileUtils;
import org.junit.Test;
import ru.arsenalpay.api.exception.ArsenalPayApiException;
import ru.arsenalpay.api.exception.InternalApiException;
import ru.arsenalpay.api.exception.PaymentException;
import ru.arsenalpay.api.response.PaymentResponse;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class PaymentResponseProducingTest {

    private PaymentResponse deserializeFromXml(String xml) throws IOException, ArsenalPayApiException {
        final File file = new File(xml);
        final String apiResponseXml = FileUtils.readFileToString(file);
        return PaymentResponse.fromXml(apiResponseXml);
    }

    @Test
    public void testOkStatus() throws Exception {
        final PaymentResponse response = deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_ok_response.xml");
        assertNotNull(response);
        assertEquals(567456755678L, response.getTransactionId().longValue());
        assertEquals(9147894125L, response.getPayerId().longValue());
        assertEquals(123456L, response.getRecipientId().longValue());
        assertTrue(new Double(52.4).equals(response.getAmount()));
        assertEquals("OK", response.getMessage());
    }

    @Test(expected = InternalApiException.class)
    public void testUnknownErrorStatus() throws Exception {
        deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_error_response.xml");
    }

    @Test(expected = InternalApiException.class)
    public void testErrorAccessStatus() throws Exception {
        deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_error_access_response.xml");
    }

    @Test(expected = PaymentException.class)
    public void testErrorAmountStatus() throws Exception {
        deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_error_amount_response.xml");
    }

    @Test(expected = PaymentException.class)
    public void testErrorCurrencyStatus() throws Exception {
        deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_error_currency_response.xml");
    }

    @Test(expected = PaymentException.class)
    public void testErrorDateformatStatus() throws Exception {
        deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_error_dateformat.xml");
    }

    @Test(expected = InternalApiException.class)
    public void testErrorFunctionStatus() throws Exception {
        deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_error_function_response.xml");
    }

    @Test(expected = InternalApiException.class)
    public void testErrorDatabaseStatus() throws Exception {
        deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_error_nodb_response.xml");
    }

    @Test(expected = PaymentException.class)
    public void testErrorPhoneStatus() throws Exception {
        deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_error_phone_response.xml");
    }

    @Test(expected = PaymentException.class)
    public void testErrorSignatureStatus() throws Exception {
        deserializeFromXml("src/test/java/ru/arsenalpay/api/unit/support/api_error_signature_response.xml");
    }

}
