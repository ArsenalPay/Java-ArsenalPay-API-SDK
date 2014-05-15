package ru.arsenalpay.api.client;

/**
 * <p>ArsenalPay server api response interface</p>
 *
 * @author adamether
 */
public interface ApiResponse {

    /**
     * Get HTTP status code
     * @see ru.arsenalpay.api.enums.HttpStatus
     * @return int status code
     */
    int getStatusCode();

    /**
     * Get HTTP response body
     * @return response body as a string
     */
    String getBody();

}
