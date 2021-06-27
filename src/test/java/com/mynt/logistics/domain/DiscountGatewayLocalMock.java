package com.mynt.logistics.domain;

import com.mynt.logistics.integration.DiscountDTO;
import com.mynt.logistics.integration.DownstreamException;
import com.mynt.logistics.integration.IDiscount;
import com.mynt.logistics.integration.gateway.IDiscountGateway;

public class DiscountGatewayLocalMock implements IDiscountGateway {
    private DiscountDTO discountDTO;
    public DiscountGatewayLocalMock(DiscountDTO discountDTO) {
        this.discountDTO = discountDTO;
    }
    @Override
    public IDiscount getDiscount(String discountCode) throws DownstreamException {
        return discountDTO;
    }
}
