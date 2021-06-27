package com.mynt.logistics.controller;

import com.mynt.logistics.domain.DiscountGatewayLocalMock;
import com.mynt.logistics.domain.Parcel;
import com.mynt.logistics.domain.ParcelConfigurationProperties;
import com.mynt.logistics.integration.DiscountDTO;
import com.mynt.logistics.integration.DiscountService;
import com.mynt.logistics.integration.DownstreamException;
import com.mynt.logistics.integration.IDiscountService;
import com.mynt.logistics.integration.gateway.IDiscountGateway;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ParcelControllerContextTest {


    ParcelController parcelController;

    Parcel parcel;

    private IDiscountGateway discountGateway;
    private IDiscountService discountService;
    private ParcelConfigurationProperties parcelConfigurationProperties;

    @Before
    public void setUp(){
        ParcelMetricsDTO parcelMetricsDTO = new ParcelMetricsDTO();
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setCode("MYNT");
        discountDTO.setDiscount(2.0);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, 1);
        discountDTO.setExpiry(new SimpleDateFormat("yyyy-mm-dd").format(now.getTime()));
        this.discountGateway = new DiscountGatewayLocalMock(discountDTO);
        this.discountService = new DiscountService(discountGateway);
        this.parcelConfigurationProperties = new ParcelConfigurationProperties();
        parcelConfigurationProperties.setHeavyParcelBaseCharge(20);
        parcelConfigurationProperties.setLargeParcelBaseCharge(0.05);
        parcelConfigurationProperties.setMediumParcelBaseCharge(0.04);
        parcelConfigurationProperties.setSmallParcelBaseCharge(0.03);
        this.parcel = new Parcel(parcelMetricsDTO, discountService, parcelConfigurationProperties);
        this.parcelController = new ParcelController(parcel);
    }

    @Test
    public void contextLoads() {
        assertNotNull(parcelController);
    }


    @Test
    public void testCalculateParcel() throws DownstreamException, ParseException {
        ParcelMetricsDTO parcelMetricsDTO = new ParcelMetricsDTO();
        parcelMetricsDTO.setHeight(25);
        parcelMetricsDTO.setLength(11);
        parcelMetricsDTO.setWidth(12);
        parcelMetricsDTO.setWeight(100);

        assertNotNull(parcelController.calculateParcel(parcelMetricsDTO));
    }

}
