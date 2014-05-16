package ru.arsenalpay.api.functional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.arsenalpay.api.client.impl.ApacheApiClientImpl;
import ru.arsenalpay.api.enums.OperationStatus;
import ru.arsenalpay.api.exception.InternalApiException;
import ru.arsenalpay.api.facade.ApiCommandsFacade;
import ru.arsenalpay.api.facade.impl.ApiCommandsFacadeImpl;
import ru.arsenalpay.api.request.PaymentRequest;
import ru.arsenalpay.api.request.PaymentStatusRequest;
import ru.arsenalpay.api.response.PaymentResponse;
import ru.arsenalpay.api.response.PaymentStatusResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

public class ApiCommandsFacadeImplTest {

    @Mock
    private CloseableHttpClient httpClientMock;

    @Mock
    private CloseableHttpResponse httpResponseMock;

    @Mock
    private StatusLine statusLineMock;

    @Mock
    private HttpEntity httpEntityMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        when(statusLineMock.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(httpResponseMock.getEntity()).thenReturn(httpEntityMock);
    }

    @Test
    public void testSuccessProcessPayment() throws Exception {
        System.out.println("ApiCommandsFacadeImplTest ---> testSuccessProcessPayment");

        File file = new File(
                "src/test/java/ru/arsenalpay/api/unit/support/api_ok_response.xml"
        );
        InputStream content = new FileInputStream(file);
        when(httpEntityMock.getContent()).thenReturn(content);
        when(httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
                new ApacheApiClientImpl(httpClientMock)
        );

        PaymentRequest paymentRequest = new PaymentRequest.MobileBuilder()
                .payerId(9140001111L)
                .recipientId(123456789L)
                .amount(1.25D)
                .currency("RUR")
                .comment("Java-SDK-Test")
                .setTestMode()
                .build();

        PaymentResponse paymentResponse = apiCommandsFacade.requestPayment(paymentRequest);

        assertNotNull(paymentResponse);
        assertEquals(567456755678L, paymentResponse.getTransactionId().longValue());
        assertEquals(9147894125L, paymentResponse.getPayerId().longValue());
        assertEquals(123456L, paymentResponse.getRecipientId().longValue());
        assertTrue(new Double(52.4).equals(paymentResponse.getAmount()));
        assertEquals("OK", paymentResponse.getMessage());

        System.out.println("Response: " + paymentResponse);
    }

    @Test(expected = InternalApiException.class)
    public void testErrorProcessPayment() throws Exception {
        System.out.println("ApiCommandsFacadeImplTest ---> testErrorProcessPayment");

        File file = new File(
                "src/test/java/ru/arsenalpay/api/unit/support/api_error_unrecognized_status_response.xml"
        );
        InputStream content = new FileInputStream(file);
        when(httpEntityMock.getContent()).thenReturn(content);
        when(httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
                new ApacheApiClientImpl(httpClientMock)
        );

        PaymentRequest paymentRequest = new PaymentRequest.MobileBuilder()
                .payerId(9140001111L)
                .recipientId(123456789L)
                .amount(1.25D)
                .currency("RUR")
                .comment("Java-SDK-Test")
                .setTestMode()
                .build();

        PaymentResponse paymentResponse = apiCommandsFacade.requestPayment(paymentRequest);
    }

    @Test
    public void testSuccessCheckPaymentStatus() throws Exception {
        System.out.println("ApiCommandsFacadeImplTest ---> testSuccessCheckPaymentStatus");

        File file = new File(
                "src/test/java/ru/arsenalpay/api/unit/support/api_ok_pay_check_payment_status_response.xml"
        );
        InputStream content = new FileInputStream(file);
        when(httpEntityMock.getContent()).thenReturn(content);
        when(httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
                new ApacheApiClientImpl(httpClientMock)
        );

        final PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest(2096L, 0L);

        final PaymentStatusResponse paymentStatusResponse = apiCommandsFacade.checkPaymentStatus(paymentStatusRequest);

        assertNotNull(paymentStatusResponse);
        assertEquals(123456789L, paymentStatusResponse.getTransactionId().longValue());
        assertEquals(123456L, paymentStatusResponse.getRecipientId().longValue());
        assertTrue(new Double(52.40).equals(paymentStatusResponse.getAmount()));
        assertEquals(9645565854L, paymentStatusResponse.getPayerId().longValue());
        assertEquals(new Date(1349060062000L), paymentStatusResponse.getDate());
        assertEquals(OperationStatus.SUCCESS, paymentStatusResponse.getMessage());
    }

    @Test
    public void testInProgressCheckPaymentStatus() throws Exception {
        System.out.println("ApiCommandsFacadeImplTest ---> testInProgressCheckPaymentStatus");

        File file = new File(
                "src/test/java/ru/arsenalpay/api/unit/support/api_in_progress_check_payment_status_response.xml"
        );
        InputStream content = new FileInputStream(file);
        when(httpEntityMock.getContent()).thenReturn(content);
        when(httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
                new ApacheApiClientImpl(httpClientMock)
        );

        final PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest(2096L, 0L);

        final PaymentStatusResponse paymentStatusResponse = apiCommandsFacade.checkPaymentStatus(paymentStatusRequest);

        assertNotNull(paymentStatusResponse);
        assertEquals(123456789L, paymentStatusResponse.getTransactionId().longValue());
        assertEquals(123456L, paymentStatusResponse.getRecipientId().longValue());
        assertTrue(new Double(52.40).equals(paymentStatusResponse.getAmount()));
        assertEquals(9645565854L, paymentStatusResponse.getPayerId().longValue());
        assertEquals(new Date(1349060062000L), paymentStatusResponse.getDate());
        assertEquals(OperationStatus.IN_PROGRESS, paymentStatusResponse.getMessage());
    }

    @Test
    public void testNotRegisteredCheckPaymentStatus() throws Exception {
        System.out.println("ApiCommandsFacadeImplTest ---> testNotRegisteredCheckPaymentStatus");

        File file = new File(
                "src/test/java/ru/arsenalpay/api/unit/support/api_not_registered_check_payment_status_response.xml"
        );
        InputStream content = new FileInputStream(file);
        when(httpEntityMock.getContent()).thenReturn(content);
        when(httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
                new ApacheApiClientImpl(httpClientMock)
        );

        final PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest(2096L, 0L);

        final PaymentStatusResponse paymentStatusResponse = apiCommandsFacade.checkPaymentStatus(paymentStatusRequest);

        assertNotNull(paymentStatusResponse);
        assertEquals(123456789L, paymentStatusResponse.getTransactionId().longValue());
        assertEquals(123456L, paymentStatusResponse.getRecipientId().longValue());
        assertTrue(new Double(52.40).equals(paymentStatusResponse.getAmount()));
        assertEquals(9645565854L, paymentStatusResponse.getPayerId().longValue());
        assertEquals(new Date(1349060062000L), paymentStatusResponse.getDate());
        assertEquals(OperationStatus.NOT_REGISTERED, paymentStatusResponse.getMessage());
    }

    @Test
    public void testRefusedCheckPaymentStatus() throws Exception {
        System.out.println("ApiCommandsFacadeImplTest ---> testRefusedCheckPaymentStatus");

        File file = new File(
                "src/test/java/ru/arsenalpay/api/unit/support/api_refused_check_payment_status_response.xml"
        );
        InputStream content = new FileInputStream(file);
        when(httpEntityMock.getContent()).thenReturn(content);
        when(httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
                new ApacheApiClientImpl(httpClientMock)
        );

        final PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest(2096L, 0L);

        final PaymentStatusResponse paymentStatusResponse = apiCommandsFacade.checkPaymentStatus(paymentStatusRequest);

        assertNotNull(paymentStatusResponse);
        assertEquals(123456789L, paymentStatusResponse.getTransactionId().longValue());
        assertEquals(123456L, paymentStatusResponse.getRecipientId().longValue());
        assertTrue(new Double(52.40).equals(paymentStatusResponse.getAmount()));
        assertEquals(9645565854L, paymentStatusResponse.getPayerId().longValue());
        assertEquals(new Date(1349060062000L), paymentStatusResponse.getDate());
        assertEquals(OperationStatus.REFUSED, paymentStatusResponse.getMessage());
    }

    @Test
    public void testInProgress2CheckPaymentStatus() throws Exception {
        System.out.println("ApiCommandsFacadeImplTest ---> testInProgress2CheckPaymentStatus");

        File file = new File(
                "src/test/java/ru/arsenalpay/api/unit/support/api_ok_init_check_payment_status.xml"
        );
        InputStream content = new FileInputStream(file);
        when(httpEntityMock.getContent()).thenReturn(content);
        when(httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
                new ApacheApiClientImpl(httpClientMock)
        );

        final PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest(2096L, 0L);

        final PaymentStatusResponse paymentStatusResponse = apiCommandsFacade.checkPaymentStatus(paymentStatusRequest);

        assertNotNull(paymentStatusResponse);
        assertEquals(123456789L, paymentStatusResponse.getTransactionId().longValue());
        assertEquals(123456L, paymentStatusResponse.getRecipientId().longValue());
        assertTrue(new Double(52.40).equals(paymentStatusResponse.getAmount()));
        assertEquals(9645565854L, paymentStatusResponse.getPayerId().longValue());
        assertEquals(new Date(1349060062000L), paymentStatusResponse.getDate());
        assertEquals(OperationStatus.IN_PROGRESS, paymentStatusResponse.getMessage());
    }

    @Test
    public void testEmptyTagPaymentStatus() throws Exception {
        System.out.println("ApiCommandsFacadeImplTest ---> testEmptyTagPaymentStatus");

        File file = new File(
                "src/test/java/ru/arsenalpay/api/unit/support/api_empty_field_payment_status_response.xml"
        );
        InputStream content = new FileInputStream(file);
        when(httpEntityMock.getContent()).thenReturn(content);
        when(httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
                new ApacheApiClientImpl(httpClientMock)
        );

        try {
            final PaymentStatusRequest request = new PaymentStatusRequest(2096L, 0L);
            final PaymentStatusResponse response = apiCommandsFacade.checkPaymentStatus(request);

            assertNotNull(response);
            assertEquals(1159374L, response.getTransactionId().longValue());
            assertNull(response.getRecipientId());
            assertNull(response.getPayerId());
            assertNull(response.getAmount());
            assertNull(response.getDate());
            assertEquals(OperationStatus.REFUSED, response.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail(e.getMessage());
        }
    }

}
