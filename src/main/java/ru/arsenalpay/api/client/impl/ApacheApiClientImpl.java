package ru.arsenalpay.api.client.impl;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import ru.arsenalpay.api.client.ApiClient;
import ru.arsenalpay.api.client.ApiResponse;
import ru.arsenalpay.api.client.ApiResponseImpl;
import ru.arsenalpay.api.command.ApiCommand;
import ru.arsenalpay.api.enums.HttpStatus;
import ru.arsenalpay.api.exception.InternalApiException;
import ru.arsenalpay.api.util.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isBlank;
import static ru.arsenalpay.api.enums.HttpMethod.GET;
import static ru.arsenalpay.api.enums.HttpMethod.POST;

/**
 *  <p>Apache Http Client impl of ApiClient interface</p>
 *
 *  <p>You can choose between your pre configured <b>httpClient</b> instance and
 *  with our building configuration witch is suitable for common usage even in a concurrency environment.</p>
 *
 *  @author adamether
 */
public class ApacheApiClientImpl implements ApiClient {

    private final CloseableHttpClient httpClient;

    private final BasicHttpContext httpContext;

    /**
     * Create ApacheApiClientImpl instance
     * @param httpClient -- configured httpClient
     */
    public ApacheApiClientImpl(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
        this.httpContext = new BasicHttpContext();
    }

    @Override
    public ApiResponse executeCommand(final ApiCommand command) throws IOException, InternalApiException {
        if (command.getHttpMethod() == GET) {
            return executeGetCommand(command);
        } else if (command.getHttpMethod() == POST) {
            return executePostCommand(command);
        } else {
            String message = String.format("Http method is not supported: [%s]", command.getHttpMethod());
            throw new InternalApiException(message);
        }
    }

    /**
     * Simply execute HTTP GET request
     * @param command -- api command
     * @return apiResponse -- apiResponse
     * @throws IOException
     * @throws ru.arsenalpay.api.exception.InternalApiException
     */
    private ApiResponse executeGetCommand(ApiCommand command) throws IOException, InternalApiException {
        HttpGet httpGet = new HttpGet(command.getFullUri());

        Logger.info("[GET] : [%s]", command.getFullUri());

        return getApiResponse(httpGet);
    }

    /**
     * Simply execute HTTP POST request
     * @param command -- api command
     * @return -- apiResponse
     * @throws IOException
     * @throws ru.arsenalpay.api.exception.InternalApiException
     */
    private ApiResponse executePostCommand(ApiCommand command) throws IOException, InternalApiException {
        HttpPost httpPost = new HttpPost(command.getBaseUri());
        // set post params using command.getParams()
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        Map<String, String> params = command.getParams();

        Logger.info("[POST] : [%s], [%s]", command.getBaseUri(), command.getParams());

        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
        return getApiResponse(httpPost);
    }

    /**
     * The real place of executing connections with server api.
     * We use <b>CloseableHttpResponse</p> witch implements <b>Closeable</b> interface for
     * managed connections closing.
     *
     * @param request -- interface that provides convenience methods to access request properties such as request URI
     * and method type
     * @return -- apiResponse
     * @throws IOException
     * @throws ru.arsenalpay.api.exception.InternalApiException
     */
    private ApiResponse getApiResponse(final HttpUriRequest request) throws IOException, InternalApiException {
        final CloseableHttpResponse httpResponse = httpClient.execute(request, httpContext);
        try {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                final int code = httpResponse.getStatusLine().getStatusCode();
                final String body = EntityUtils.toString(entity);

                Logger.info("[HTTP STATUS]: [%s], [HTTP RESPONSE]: [%s]", code, body);

                if (isBlank(body)) {
                    throw new InternalApiException("Api response is blank.");
                }
                if (HttpStatus.error(code)) {
                    String message = String.format("Error: [%s], message: [%s].", code, body);
                    throw new InternalApiException(message);
                }
                return new ApiResponseImpl(code, body);
            }
        } finally {
            httpResponse.close();
        }
        return ApiResponseImpl.createEmpty();
    }

}
