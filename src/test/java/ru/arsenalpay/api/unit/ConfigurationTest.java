package ru.arsenalpay.api.unit;

import org.junit.Before;
import org.junit.Test;
import ru.arsenalpay.api.util.Configuration;

import static org.junit.Assert.*;

public class ConfigurationTest {

    private Configuration config;

    @Before
    public void setUp() throws Exception {
        config = Configuration.getTestInstance();
    }

    @Test
    public void testGetProperty() throws Exception {
        System.out.println("ConfigurationTest ---> testGetProperty");

        // case 1: getting existing value from test config
        String propertyA = config.getProperty("keyA");
        assertNotNull(propertyA);
        assertEquals("valueA", propertyA);

        // case 2: this one is not in the config (should be taken from the default)
        String propertyB = config.getProperty("keyB", "valueB");
        assertNotNull(propertyB);
        assertEquals("valueB", propertyB);

        // case 3:  keyC doesn't exist
        assertNull(config.getProperty("keyC"));
    }

}
