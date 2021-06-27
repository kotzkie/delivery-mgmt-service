package com.mynt.logistics.domain;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "parcel")
public class ParcelConfigurationProperties {
    @Value("${heavyParcelBaseCharge:20}")
    private double heavyParcelBaseCharge;
    @Value("${smallParcelBaseCharge:0.03}")
    private double smallParcelBaseCharge;
    @Value("${mediumParcelBaseCharge:0.04}")
    private double mediumParcelBaseCharge;
    @Value("${largeParcelBaseCharge:0.05}")
    private double largeParcelBaseCharge;
}
