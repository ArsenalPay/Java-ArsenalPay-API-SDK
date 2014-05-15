package ru.arsenalpay.api.unit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ru.arsenalpay.api.util.SecurityUtils.getSignature;

public class SecurityUtilsTest {

    @Test
    public void testSignatureInitPayMk() throws Exception {
        String signature = getSignature("123456", "2096", "init_pay_mk", "123456789", "9140001111", "1.25");
        assertEquals("1e304e4920f14ea2ada67e5db2e39b1d", signature);
    }

    @Test
    public void testSignatureInitPayMkStatus() throws Exception {
        final String signature = getSignature("123456", "2096", "init_pay_mk_status", "123456");
        assertEquals("388149ef1331aaf88910db84737784f0", signature);
    }

}
