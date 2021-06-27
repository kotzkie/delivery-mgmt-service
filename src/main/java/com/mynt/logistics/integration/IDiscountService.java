package com.mynt.logistics.integration;

public interface IDiscountService {
   IDiscount getDiscount(String discountCode) throws DownstreamException;
}
