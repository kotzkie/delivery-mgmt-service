package com.mynt.logistics.domain.parcel.rules;

import com.mynt.logistics.domain.Parcel;
import com.mynt.logistics.domain.parcel.rules.IParcelRule;

public class SmallParcelRule implements IParcelRule {


    private boolean isValid;

    private double baseCharge;

    public SmallParcelRule() {}


    public SmallParcelRule(double baseCharge) {
        this.baseCharge = baseCharge;
    }

    @Override
    public boolean isValid() {
        return this.isValid;
    }


    @Override
    public void evaluate(Parcel parcel) {
        if (parcel.getVolume() < 1500) {
            parcel.setPriority(3);
            parcel.setWeightClassification("Small Parcel");
            parcel.setCost(this.baseCharge * parcel.getVolume());
            isValid = true;
        }
    }

}
