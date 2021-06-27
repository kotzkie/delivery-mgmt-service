package com.mynt.logistics.integration;

import com.mynt.logistics.integration.gateway.DiscountGateway;
import com.mynt.logistics.integration.gateway.IDiscountGateway;
import com.mynt.logistics.integration.properties.DiscountServiceProperties;
import com.mynt.logistics.integration.properties.DownstreamProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountGatewayTest {

    private IDiscountGateway discountGateway;

    @Before
    public void setUp() {
        DiscountServiceProperties discountServiceProperties = new DiscountServiceProperties();
        discountServiceProperties.setHost("mynt-exam.mocklab.io");
        discountServiceProperties.setApikey("apikey");
        discountServiceProperties.setProtocol("https");
        discountServiceProperties.setPort("443");
        discountServiceProperties.setTimeout(5);
        DownstreamProperties downstreamProperties = new DownstreamProperties();
        downstreamProperties.setTimeout(5);
        DownstreamConfig downstreamConfig = new DownstreamConfig(downstreamProperties);
        this.discountGateway  = new DiscountGateway( downstreamConfig.getHttpClient() , discountServiceProperties);
    }

    @Test
    @DisplayName("Test creation of Discount gateway object")
    public void testDiscountGatewayCreate() {
        assertNotNull(this.discountGateway);
    }

    @Test
    @DisplayName("Test getDiscount method from Discount gateway object")
    public void testGetDiscountFromDiscountGateway() throws DownstreamException {
        assertNotNull(this.discountGateway.getDiscount("MYNT"));
    }

    @Test
    @DisplayName("Test getDiscount exception method from Discount gateway object")
    public void testGetDiscountExcetptionFromDiscountGatewayE() throws DownstreamException {
        assertThrows(DownstreamException.class, () -> {
            this.discountGateway.getDiscount("mynt"); });
    }

    @Test
    @DisplayName("Test getDiscount with invalid host -> IO exception method from Discount gateway object")
    public void testGetDiscountInvalidHostIOExcetptionFromDiscountGateway() throws DownstreamException {
        DiscountServiceProperties discountServiceProperties = new DiscountServiceProperties();
        discountServiceProperties.setHost("notValidURL");
        discountServiceProperties.setApikey("apikey");
        discountServiceProperties.setProtocol("https");
        discountServiceProperties.setPort("443");
        discountServiceProperties.setTimeout(5);
        DownstreamProperties downstreamProperties = new DownstreamProperties();
        downstreamProperties.setTimeout(5);
        DownstreamConfig downstreamConfig = new DownstreamConfig(downstreamProperties);
        DiscountGateway discountGateway  = new DiscountGateway( downstreamConfig.getHttpClient() , discountServiceProperties);
        assertThrows(DownstreamException.class, () -> {
            this.discountGateway.getDiscount("mynt"); });
    }

    @Test
    @DisplayName("Test getDiscount with invalid key -> IO exception method from Discount gateway object")
    public void testGetDiscountInvalidKeyIOExcetptionFromDiscountGateway() throws DownstreamException {
        DiscountServiceProperties discountServiceProperties = new DiscountServiceProperties();
        discountServiceProperties.setHost("mynt-exam.mocklab.io");
        discountServiceProperties.setApikey("99999999");
        discountServiceProperties.setProtocol("https");
        discountServiceProperties.setPort("443");
        discountServiceProperties.setTimeout(5);
        DownstreamProperties downstreamProperties = new DownstreamProperties();
        downstreamProperties.setTimeout(5);
        DownstreamConfig downstreamConfig = new DownstreamConfig(downstreamProperties);
        DiscountGateway discountGateway  = new DiscountGateway( downstreamConfig.getHttpClient() , discountServiceProperties);
        assertThrows(DownstreamException.class, () -> {
            this.discountGateway.getDiscount("mynt"); });
    }
}
