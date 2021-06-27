package com.mynt.logistics.integration;

import com.mynt.logistics.integration.properties.DiscountServiceProperties;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountServicePropertiesTest {

    @Test
    @DisplayName("Test creation of discount service properties object")
    public void testDiscountServicePropertiesInstanceCreate() {
        assertNotNull(new DiscountServiceProperties());
    }

    @Test
    @DisplayName("Test attribute's setters of discount service properties")
    public void testDiscountServicePropertiesAttributeSetters() {
        DiscountServiceProperties discountServiceProperties = new DiscountServiceProperties();
        discountServiceProperties.setHost("myapi.com");
        discountServiceProperties.setApikey("apikey");
        discountServiceProperties.setProtocol("https");
        discountServiceProperties.setPort("443");
        assertEquals("myapi.com", discountServiceProperties.getHost());
        assertEquals("apikey", discountServiceProperties.getApikey());
        assertEquals("https", discountServiceProperties.getProtocol());
        assertEquals("443", discountServiceProperties.getPort());
    }

}
