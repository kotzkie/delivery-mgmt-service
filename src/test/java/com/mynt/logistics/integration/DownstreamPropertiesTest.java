package com.mynt.logistics.integration;

import com.mynt.logistics.integration.properties.DownstreamProperties;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DownstreamPropertiesTest {


    @Test
    @DisplayName("Test creation of Downstream properties object")
    public void testDownstreamPropertiesInstanceCreate() {
        assertNotNull(new DownstreamProperties());
    }

    @Test
    @DisplayName("Test attribute's setters of discount service properties")
    public void testDiscountServicePropertiesAttributeSetters() {
        DownstreamProperties downstreamProperties = new DownstreamProperties();
        downstreamProperties.setTimeout(5);
        assertEquals(5, downstreamProperties.getTimeout());
    }

}
