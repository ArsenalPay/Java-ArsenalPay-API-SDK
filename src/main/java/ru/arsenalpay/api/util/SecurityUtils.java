package ru.arsenalpay.api.util;

import org.apache.commons.codec.digest.DigestUtils;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * <p>Simple SecurityUtils class for ArsenalPay API SDK.</p>
 *
 * @author adamether
 */
public class SecurityUtils {

    public static String md5(String data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * Create signature for request values
     * @param secret secret word
     * @param values values in strict order as described in PS API
     * @return signature
     */
    public static String getSignature(String secret, String... values) {
        if (isBlank(secret)) {
            throw new IllegalArgumentException("The value of 'secret' can't be blank.");
        }
        if (values == null || values.length < 1) {
            throw new IllegalArgumentException("The 'values' can't be null or empty.");
        }

        final StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(md5(value));
        }
        sb.append(md5(secret));
        return md5(sb.toString());
    }

}
