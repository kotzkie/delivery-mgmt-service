package com.mynt.logistics.integration;

import lombok.Data;

import java.io.Serializable;


@Data
public class DiscountDTO implements Serializable, IDiscount {

    private static final long serialVersionUID = 8202021169326534924L;

    private String code;
    private double discount;
    private String expiry;

    private String error;
}