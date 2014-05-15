package ru.arsenalpay.api.command;

import ru.arsenalpay.api.enums.HttpMethod;

import java.text.MessageFormat;
import java.util.Map;

import static ru.arsenalpay.api.util.RequestUtils.mapToQueryString;

/**
 * <p>ApiCommand is the model of ArsenalPay Server API request.</p>
 *
 * <span>Contains three components:</span>
 * <ul>
 *      <li>base uri -- "https://" + host + method (server location) </li>
 *      <li>params   -- request params for creating query string for GET or body for POST</li>
 *      <li>fullUri  -- optional component for HTTP GET METHOD</li>
 * </ul>
 *
 * @see ru.arsenalpay.api.command.ApiCommandProducer
 *
 * @author adamether
 */
public final class ApiCommand {

    /**
     * "https://" + host + method (api server location),  ex: "https://arsenalpay.ru/init_pay_mk/"
     */
    private final String baseUri;

    /**
     * the map of command params
     */
    private final Map<String, String> params;

    /**
     * http method (using GET / POST), prefered POST
     * @see ru.arsenalpay.api.enums.HttpMethod
     */
    private final HttpMethod httpMethod;

    /**
     * fullUri = baseUri + params as query string
     */
    private final String fullUri;

    /**
     * Create apiCommand
     * @param baseUri    -- "https://" + host + method (api server location),  ex: "https://arsenalpay.ru/init_pay_mk/"
     * @param params     -- the map of command params
     * @param httpMethod -- http method (using GET / POST), prefered POST
     */
    public ApiCommand(String baseUri, Map<String, String> params, HttpMethod httpMethod) {
        this.baseUri = baseUri;
        this.params = params;
        this.httpMethod = httpMethod;
        this.fullUri = createFullUri(baseUri, params);
    }

    private String createFullUri(String baseUri, Map<String, String> params) {
        return MessageFormat.format("{0}{1}{2}", baseUri, "?", mapToQueryString(params));
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getFullUri() {
        return fullUri;
    }

}
