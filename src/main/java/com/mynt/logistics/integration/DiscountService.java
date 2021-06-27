package com.mynt.logistics.integration;


import com.mynt.logistics.integration.gateway.IDiscountGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DiscountService implements IDiscountService{

    private IDiscountGateway discountGateway;

    @Autowired
    public DiscountService(IDiscountGateway discountGateway) {
        this.discountGateway = discountGateway;
    }

    @Override
    public IDiscount getDiscount(String discountCode) throws DownstreamException {
        return discountGateway.getDiscount(discountCode);
    }
}
