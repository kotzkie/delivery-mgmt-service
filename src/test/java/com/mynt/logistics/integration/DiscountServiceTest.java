package com.mynt.logistics.integration;

import com.mynt.logistics.domain.DiscountGatewayLocalMock;
import com.mynt.logistics.integration.gateway.IDiscountGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DiscountServiceTest {

    private IDiscountGateway discountGateway;

    private IDiscountService discountService;



    @Before
    public void setUp() {
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setCode("MYNT");
        discountDTO.setDiscount(2.0);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, 1);
        discountDTO.setExpiry(new SimpleDateFormat("yyyy-mm-dd").format(now.getTime()));
        this.discountService  = new DiscountService(new DiscountGatewayLocalMock(discountDTO));
    }

    @Test
    @DisplayName("Test creation of Discount service object")
    public void testDiscountServiceCreate() {
        assertNotNull(this.discountService);
    }

    @Test
    @DisplayName("Test getDiscount method from Discount service object")
    public void testgetDiscountFromDiscountService() throws DownstreamException {
        DiscountDTO discountDTO = (DiscountDTO) this.discountService.getDiscount("MYNT");
        assertNotNull(discountDTO);
        assertEquals("MYNT", discountDTO.getCode());
        assertEquals(2.0, discountDTO.getDiscount());
        assertNotNull(discountDTO.getExpiry());
    }
}
