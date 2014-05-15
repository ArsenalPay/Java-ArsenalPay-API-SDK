package ru.arsenalpay.api.util;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;

/**
 * <p>Simple RequestUtils class.</p>
 *
 * @author adamether
 */
public class RequestUtils {

    /**
     * Transform URI to map
     * @param uri of view '/?key1=value1&key2=value2...'
     */
    public static Map<String, String> uriToMap(String uri) {
        if (StringUtils.isBlank(uri)) {
            return null;
        }
        try {
            // LinkedHashMap is our impl choice, because order of params is awesome sometimes
            Map<String, String> params = new LinkedHashMap<String, String>();
            String query = "";

            // remove '/?' from uri if need
            if (uri.contains("?")) {
                String[] urlParts = uri.split("\\?");
                if (urlParts.length > 1) {
                    query = urlParts[1].trim();
                }
            } else {
                query = uri;
            }

            // split string to key value pair and put to map
            for (String param : query.split("&")) {
                String[] pair = param.split("=");

                String key;
                try {
                    key = URLDecoder.decode(pair[0], "UTF-8");
                } catch (IllegalArgumentException e) {
                    key = pair[0];
                }

                String value = "";
                if (pair.length > 1) {
                    try {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    } catch (IllegalArgumentException e) {
                        key = pair[1];
                    }
                }
                boolean isValueExist = params.get(key) != null;
                if (!isValueExist) {
                    params.put(key, value);
                }
            }
            return params;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Transform map to string of view '/?key1=value1&key2=value2...'
     */
    public static String mapToUri(Map<String, String> map, boolean shouldEncode) {
        return "/?" + mapToQueryString(map, shouldEncode);
    }

    /**
     * Transform map to string of view '/?key1=value1&key2=value2...'
     * with shouldEncode -- true
     */
    public static String mapToUri(Map<String, String> map) {
        return mapToUri(map, true);
    }

    /**
     * Transform map to queryString of view 'key1=value1&key2=value2...'
     */
    public static String mapToQueryString(Map<String, String> map) {
        return mapToQueryString(map, false);
    }

    /**
     * Transform map to queryString of view 'key1=value1&key2=value2...'
     * @param map source map
     * @param shouldEncode if set true encode this using URLEncoder in 'UTF-8'
     * @return query string
     */
    public static String mapToQueryString(Map<String, String> map, boolean shouldEncode) {
        List<String> uriList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                String value = entry.getValue();
                if (shouldEncode) {
                    value = urlEncodeUTF8(value);
                }
                uriList.add(MessageFormat.format("{0}={1}", entry.getKey(), value));
            }
        }
        return StringUtils.join(uriList, "&");
    }

    /**
     * Encode url with UTF-8 charset
     * @param value input data
     * @return encoded ulr in UTF8
     */
    private static String urlEncodeUTF8(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
