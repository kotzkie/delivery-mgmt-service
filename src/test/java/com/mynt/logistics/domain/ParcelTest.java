package com.mynt.logistics.domain;

import com.mynt.logistics.controller.ParcelMetricsDTO;
import com.mynt.logistics.integration.*;
import com.mynt.logistics.integration.gateway.IDiscountGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParcelTest {

    private Parcel parcel;
    private IDiscountGateway discountGateway;
    private IDiscountService discountService;
    private ParcelConfigurationProperties parcelConfigurationProperties;

    @Before
    public void setUp() {
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
    }

    private DiscountGatewayLocalMock getExpiredDiscountVoucher() {
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setCode("MYNT");
        discountDTO.setDiscount(2.0);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, -1);
        discountDTO.setExpiry(new SimpleDateFormat("yyyy-mm-dd").format(now.getTime()));
        return  new DiscountGatewayLocalMock(discountDTO);
    }

    @Test
    @DisplayName("Test constructor of parcel used by springs container")
    public void testConstuctorWithDiscountServiceAndParcelPropertiesInstance() {
        assertNotNull(new Parcel(this.discountService, this.parcelConfigurationProperties) );
    }

    @Test
    @DisplayName("Test rejected parcel cost evaluation")
    public void testRejectedParcel() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(50);
        this.parcel.getParcel().setLength(50);
        this.parcel.getParcel().setWeight(100);
        this.parcel.getParcel().setWidth(50);
        assertEquals(0.0,this.parcel.calculateCost());
        assertEquals("Reject",this.parcel.getWeightClassification());
    }

    @Test
    @DisplayName("Test rejected parcel cost evaluation with discount code")
    public void testRejectedParcelWithDiscountCode() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(50);
        this.parcel.getParcel().setLength(50);
        this.parcel.getParcel().setWeight(100);
        this.parcel.getParcel().setWidth(50);
        this.parcel.getParcel().setVoucher("MYNT");
        assertEquals(0.0,this.parcel.calculateCost());
        assertEquals("Reject",this.parcel.getWeightClassification());
    }

    @Test
    @DisplayName("Test heavy parcel cost evaluation")
    public void testHeavyParcel() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(50);
        this.parcel.getParcel().setLength(50);
        this.parcel.getParcel().setWeight(50);
        this.parcel.getParcel().setWidth(50);
        assertEquals(1000.0,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test heavy parcel cost evaluation with discount code")
    public void testHeavyParcelWithDiscountCode() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(50);
        this.parcel.getParcel().setLength(50);
        this.parcel.getParcel().setWeight(50);
        this.parcel.getParcel().setWidth(50);
        this.parcel.getParcel().setVoucher("MYNT");
        assertEquals(980.0,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test heavy parcel cost evaluation with expired discount code")
    public void testHeavyParcelWithExpiredDiscountCode() throws DownstreamException, ParseException {
        ParcelMetricsDTO parcelMetricsDTO = new ParcelMetricsDTO();
        parcelMetricsDTO.setHeight(50);
        parcelMetricsDTO.setLength(50);
        parcelMetricsDTO.setWeight(50);
        parcelMetricsDTO.setWidth(50);
        parcelMetricsDTO.setVoucher("MYNT");
        this.discountService = new DiscountService(getExpiredDiscountVoucher());
        this.parcel = new Parcel(parcelMetricsDTO, discountService, this.parcelConfigurationProperties);
        assertEquals(1000.0,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test large parcel cost evaluation")
    public void testLargeParcel() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(50);
        this.parcel.getParcel().setLength(50);
        this.parcel.getParcel().setWeight(10);
        this.parcel.getParcel().setWidth(50);
        assertEquals(6250.0,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test large parcel cost evaluation with discount code")
    public void testLargeParcelWithDiscountCode() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(50);
        this.parcel.getParcel().setLength(50);
        this.parcel.getParcel().setWeight(10);
        this.parcel.getParcel().setWidth(50);
        this.parcel.getParcel().setVoucher("MYNT");
        assertEquals(6125.0,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test large parcel cost evaluation with expired discount code")
    public void testLargeParcelWithExpiredDiscountCode() throws DownstreamException, ParseException {
        ParcelMetricsDTO parcelMetricsDTO = new ParcelMetricsDTO();
        parcelMetricsDTO.setHeight(50);
        parcelMetricsDTO.setLength(50);
        parcelMetricsDTO.setWeight(10);
        parcelMetricsDTO.setWidth(50);
        parcelMetricsDTO.setVoucher("MYNT");
        this.discountService = new DiscountService(getExpiredDiscountVoucher());
        this.parcel = new Parcel(parcelMetricsDTO, discountService, this.parcelConfigurationProperties);
        assertEquals(6250.0,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test medium parcel cost evaluation")
    public void testMediumParcel() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(10);
        this.parcel.getParcel().setLength(15);
        this.parcel.getParcel().setWeight(10);
        this.parcel.getParcel().setWidth(15);
        assertEquals(90.0,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test medium parcel cost evaluation with discount code")
    public void testMediumParcelWithDiscountCode() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(10);
        this.parcel.getParcel().setLength(15);
        this.parcel.getParcel().setWeight(10);
        this.parcel.getParcel().setWidth(15);
        this.parcel.getParcel().setVoucher("MYNT");
        assertEquals(88.2,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test medium parcel cost evaluation with expired discount code")
    public void testMediumParcelWithExpiredDiscountCode() throws DownstreamException, ParseException {
        ParcelMetricsDTO parcelMetricsDTO = new ParcelMetricsDTO();
        parcelMetricsDTO.setHeight(10);
        parcelMetricsDTO.setLength(15);
        parcelMetricsDTO.setWeight(10);
        parcelMetricsDTO.setWidth(15);
        parcelMetricsDTO.setVoucher("MYNT");
        this.discountService = new DiscountService(getExpiredDiscountVoucher());
        this.parcel = new Parcel(parcelMetricsDTO, discountService, this.parcelConfigurationProperties);
        assertEquals(90.0,this.parcel.calculateCost());
    }


    @Test
    @DisplayName("Test small parcel cost evaluation")
    public void testSmallParcel() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(10);
        this.parcel.getParcel().setLength(10);
        this.parcel.getParcel().setWeight(10);
        this.parcel.getParcel().setWidth(10);
        assertEquals(30.0,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test small parcel cost evaluation with discount code")
    public void testSmallParcelWithDiscountCode() throws DownstreamException, ParseException {
        this.parcel.getParcel().setHeight(10);
        this.parcel.getParcel().setLength(10);
        this.parcel.getParcel().setWeight(10);
        this.parcel.getParcel().setWidth(10);
        this.parcel.getParcel().setVoucher("MYNT");
        assertEquals(29.4,this.parcel.calculateCost());
    }

    @Test
    @DisplayName("Test small parcel cost evaluation with expired discount code")
    public void testSmallParcelWithExpiredDiscountCode() throws DownstreamException, ParseException {
        ParcelMetricsDTO parcelMetricsDTO = new ParcelMetricsDTO();
        parcelMetricsDTO.setHeight(10);
        parcelMetricsDTO.setLength(10);
        parcelMetricsDTO.setWeight(10);
        parcelMetricsDTO.setWidth(10);
        parcelMetricsDTO.setVoucher("MYNT");
        this.discountService = new DiscountService(getExpiredDiscountVoucher());
        this.parcel = new Parcel(parcelMetricsDTO, discountService, this.parcelConfigurationProperties);
        assertEquals(30.0,this.parcel.calculateCost());
    }
}
