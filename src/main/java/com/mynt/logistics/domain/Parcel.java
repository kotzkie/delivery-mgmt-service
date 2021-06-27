package com.mynt.logistics.domain;

import com.mynt.logistics.controller.ParcelMetricsDTO;
import com.mynt.logistics.domain.parcel.rules.*;
import com.mynt.logistics.integration.DiscountDTO;
import com.mynt.logistics.integration.DownstreamException;
import com.mynt.logistics.integration.IDiscountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.annotation.RequestScope;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RequestScope
@Component
public class Parcel {
    private ParcelMetricsDTO parcel;
    private double cost;
    private Integer volume;
    private String weightClassification;
    private int priority;
    private IDiscountService discountService;
    private ParcelConfigurationProperties parcelConfigurationProperties;

    @Autowired
    public Parcel(IDiscountService discountService, ParcelConfigurationProperties parcelConfigurationProperties) {
        this.discountService = discountService;
        this.parcelConfigurationProperties = parcelConfigurationProperties;
    }

    public Parcel(ParcelMetricsDTO parcel , IDiscountService discountService, ParcelConfigurationProperties parcelConfigurationProperties) {
        this.parcel = parcel;
        this.discountService = discountService;
        this.parcelConfigurationProperties = parcelConfigurationProperties;
    }
    public double calculateCost() throws DownstreamException, ParseException {
        log.debug(parcelConfigurationProperties.toString());
        List<IParcelRule> parcelRuleList = new ArrayList<>();
        parcelRuleList.add(new RejectParcelRule());
        parcelRuleList.add(new HeaveyParcelRule(parcelConfigurationProperties.getHeavyParcelBaseCharge()));
        parcelRuleList.add(new SmallParcelRule(parcelConfigurationProperties.getSmallParcelBaseCharge()));
        parcelRuleList.add(new MediumParcelRule(parcelConfigurationProperties.getMediumParcelBaseCharge()));
        parcelRuleList.add(new LargestParcelRule(parcelConfigurationProperties.getLargeParcelBaseCharge()));

        for ( IParcelRule rule : parcelRuleList) {
            rule.evaluate(this);
            if (rule.isValid() || "Reject".equalsIgnoreCase(this.getWeightClassification())) break;
        }

        if (!ObjectUtils.isEmpty(parcel.getVoucher())) {
            applyDiscount(parcel.getVoucher());
        }
        return this.cost;
    }

     private void applyDiscount(String discountCode) throws DownstreamException, ParseException {
       DiscountDTO discount = (DiscountDTO) discountService.getDiscount(discountCode);
       Date expiryDate = new SimpleDateFormat("yyyy-mm-dd").parse(discount.getExpiry());
      if ( new Date().compareTo(expiryDate) <= 0 ) {
          this.cost  = this.cost - (  this.cost * (discount.getDiscount()/100));
      } else {
          log.warn("Expired discount code {} is used.", discountCode);
      }

    }


    public ParcelMetricsDTO getParcel() {
        return this.parcel;
    }

    public double getVolume() {
        return  parcel.getHeight() *  parcel.getWidth() *  parcel.getLength();
    }

    public double getWeight( ) {
        return this.parcel.getWeight();
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getWeightClassification() {
        return this.weightClassification;
    }
    public void setWeightClassification(String weightClassification) {
        this.weightClassification = weightClassification;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setParcelMetricsDTO(ParcelMetricsDTO parcelMetricsDTO) {
        this.parcel = parcelMetricsDTO;
    }
}
