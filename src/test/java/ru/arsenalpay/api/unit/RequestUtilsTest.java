package ru.arsenalpay.api.unit;

import org.junit.Test;
import ru.arsenalpay.api.util.RequestUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RequestUtilsTest {

    @Test
    public void testMapToQueryString() throws Exception {
        final Map<String, String> map = new LinkedHashMap<String, String>() {{
            put("SIGN", "0328aa7ef0fc6cbaa8b8b7000f1aa5ba");
            put("PHONE", "9140001111");
            put("FUNCTION", "init_pay_mk");
            put("CURRENCY", "RUR");
            put("ID", "2096");
            put("AMOUNT", "1.25");
            put("ACCOUNT", "123456789");
        }};

        String expectedUri =
                "SIGN=0328aa7ef0fc6cbaa8b8b7000f1aa5ba" +
                        "&PHONE=9140001111" +
                        "&FUNCTION=init_pay_mk" +
                        "&CURRENCY=RUR" +
                        "&ID=2096" +
                        "&AMOUNT=1.25" +
                        "&ACCOUNT=123456789";

        final String uri = RequestUtils.mapToQueryString(map);
        assertEquals(expectedUri, uri);
    }

    @Test
    public void testMapToUri() throws Exception {
        final Map<String, String> map = new LinkedHashMap<String, String>() {{
            put("SIGN", "0328aa7ef0fc6cbaa8b8b7000f1aa5ba");
            put("PHONE", "9140001111");
            put("FUNCTION", "init_pay_mk");
            put("CURRENCY", "RUR");
            put("ID", "2096");
            put("AMOUNT", "1.25");
            put("ACCOUNT", "123456789");
        }};

        String expectedUri =
                "/?SIGN=0328aa7ef0fc6cbaa8b8b7000f1aa5ba" +
                "&PHONE=9140001111" +
                "&FUNCTION=init_pay_mk" +
                "&CURRENCY=RUR" +
                "&ID=2096" +
                "&AMOUNT=1.25" +
                "&ACCOUNT=123456789";

        final String uri = RequestUtils.mapToUri(map);
        assertEquals(expectedUri, uri);
    }

    @Test
    public void testParseUriToMap() throws Exception {
        String uri =
                "/?SIGN=0328aa7ef0fc6cbaa8b8b7000f1aa5ba" +
                        "&PHONE=9140001111" +
                        "&FUNCTION=init_pay_mk" +
                        "&CURRENCY=RUR" +
                        "&ID=2096" +
                        "&AMOUNT=1.25" +
                        "&ACCOUNT=123456789";

        final Map<String, String> expectedMap = new LinkedHashMap<String, String>() {{
            put("SIGN", "0328aa7ef0fc6cbaa8b8b7000f1aa5ba");
            put("PHONE", "9140001111");
            put("FUNCTION", "init_pay_mk");
            put("CURRENCY", "RUR");
            put("ID", "2096");
            put("AMOUNT", "1.25");
            put("ACCOUNT", "123456789");
        }};

        final Map<String, String> map = RequestUtils.uriToMap(uri);
        assertEquals(expectedMap, map);
    }

    @Test
    public void testParseQueryStringToMap() throws Exception {
        String uri =
                "SIGN=0328aa7ef0fc6cbaa8b8b7000f1aa5ba" +
                        "&PHONE=9140001111" +
                        "&FUNCTION=init_pay_mk" +
                        "&CURRENCY=RUR" +
                        "&ID=2096" +
                        "&AMOUNT=1.25" +
                        "&ACCOUNT=123456789";

        final Map<String, String> expectedMap = new LinkedHashMap<String, String>() {{
            put("SIGN", "0328aa7ef0fc6cbaa8b8b7000f1aa5ba");
            put("PHONE", "9140001111");
            put("FUNCTION", "init_pay_mk");
            put("CURRENCY", "RUR");
            put("ID", "2096");
            put("AMOUNT", "1.25");
            put("ACCOUNT", "123456789");
        }};

        final Map<String, String> map = RequestUtils.uriToMap(uri);
        assertEquals(expectedMap, map);
    }

}
