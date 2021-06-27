package com.mynt.logistics.integration;

import com.mynt.logistics.integration.properties.DownstreamProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DownstreamConfigTest {

    private DownstreamProperties downstreamProperties;
    private DownstreamConfig downstreamConfig;

    @Before
    public void setUp() {
        DownstreamProperties downstreamProperties = new DownstreamProperties();
        downstreamProperties.setTimeout(5);
        downstreamConfig = new DownstreamConfig(downstreamProperties);
    }

    @Test
    @DisplayName("Test creation of Downstream config object")
    public void testDownstreamConfigCreate() {

        assertNotNull(downstreamConfig);
    }

    @Test
    @DisplayName("Test creation of httpclient object")
    public void testDownstreamConfigGetHttpClientCreate() {
        assertNotNull(downstreamConfig.getHttpClient());
    }


}
