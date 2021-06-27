package com.mynt.logistics.integration.gateway;

import com.mynt.logistics.integration.DownstreamException;
import com.mynt.logistics.integration.IDiscount;

public interface IDiscountGateway {
   IDiscount getDiscount(String discountCode) throws DownstreamException;
}
