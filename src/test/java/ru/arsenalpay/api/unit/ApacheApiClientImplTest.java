package ru.arsenalpay.api.unit;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.arsenalpay.api.client.ApiClient;
import ru.arsenalpay.api.client.ApiResponse;
import ru.arsenalpay.api.client.impl.ApacheApiClientImpl;
import ru.arsenalpay.api.command.ApiCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static ru.arsenalpay.api.enums.HttpMethod.GET;
import static ru.arsenalpay.api.enums.HttpMethod.POST;

public class ApacheApiClientImplTest {

    public static final String BASE_URI = "https://arsenalpay.ru/init_pay_mk/";

    @Mock
    private CloseableHttpClient httpClientMock;

    @Mock
    private CloseableHttpResponse httpResponseMock;

    @Mock
    private StatusLine statusLineMock;

    @Mock
    private HttpEntity httpEntityMock;

    private Map<String, String> params;
    private String apiResponseBody;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        final File file = new File("src/test/java/ru/arsenalpay/api/unit/support/api_ok_response.xml");
        InputStream content = new FileInputStream(file);
        apiResponseBody = FileUtils.readFileToString(file);

        when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        when(statusLineMock.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(httpResponseMock.getEntity()).thenReturn(httpEntityMock);
        when(httpEntityMock.getContent()).thenReturn(content);

        params = new HashMap<String, String>() {{
            put("SIGN", "0328aa7ef0fc6cbaa8b8b7000f1aa5ba");
            put("PHONE", "9140001111");
            put("FUNCTION", "init_pay_mk");
            put("CURRENCY", "RUR");
            put("ID", "2096");
            put("AMOUNT", "1.25");
            put("ACCOUNT", "123456789");
        }};
    }

    @Test
    public void testExecuteCommandGet() throws Exception {
        System.out.println("ApacheApiClientImplTest ---> testExecuteCommandGet");

        when(httpClientMock.execute(isA(HttpGet.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiClient apiClient = new ApacheApiClientImpl(httpClientMock);
        ApiCommand apiCommand = new ApiCommand(BASE_URI, params, GET);
        final ApiResponse apiResponse = apiClient.executeCommand(apiCommand);

        assertNotNull(apiResponse);
        assertEquals(HttpStatus.SC_OK, apiResponse.getStatusCode());
        assertEquals(apiResponseBody, apiResponse.getBody());

        System.out.println("RESP: " + apiResponse);
    }

    @Test
    public void testExecuteCommandPost() throws Exception {
        System.out.println("ApacheApiClientImplTest ---> testExecuteCommandPOST");

        when(httpClientMock.execute(isA(HttpPost.class), isA(HttpContext.class))).thenReturn(httpResponseMock);

        ApiClient apiClient = new ApacheApiClientImpl(httpClientMock);
        ApiCommand apiCommand = new ApiCommand(BASE_URI, params, POST);
        final ApiResponse apiResponse = apiClient.executeCommand(apiCommand);

        assertNotNull(apiResponse);
        assertEquals(HttpStatus.SC_OK, apiResponse.getStatusCode());
        assertEquals(apiResponseBody, apiResponse.getBody());

        System.out.println("RESP: " + apiResponse);
    }

}
