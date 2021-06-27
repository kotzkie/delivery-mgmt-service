package com.mynt.logistics.controller;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class ParcelResponseDTO implements Serializable {
    private static final long serialVersionUID = 7909686160277740229L;
    private double cost;
    private String weightClassification;
}
