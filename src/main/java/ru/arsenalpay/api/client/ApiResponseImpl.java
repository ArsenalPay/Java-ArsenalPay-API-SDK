package ru.arsenalpay.api.client;

/**
 * <p>ArsenalPay server api response impl </p>
 * The simplest implementation Api Response which can be imagined.
 *
 * @author adamether
 */
public final class ApiResponseImpl implements ApiResponse {

    private final Integer statusCode;
    private final String body;

    /**
     * Constructor for ApiResponseImpl
     * @param statusCode -- http status code
     * @param body -- api response body
     */
    public ApiResponseImpl(Integer statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    /**
     * Create empty apiResponseImpl object that
     * contains <b>statusCode and body which are null</b>
     * @return empty apiResponseImpl
     */
    public static ApiResponseImpl createEmpty() {
        return new ApiResponseImpl(null, null);
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "ApiResponseImpl{" +
                "statusCode=" + statusCode +
                ", body='" + body + '\'' +
                '}';
    }

}
