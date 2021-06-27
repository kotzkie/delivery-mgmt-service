package com.mynt.logistics.domain.parcel.rules;

import com.mynt.logistics.domain.Parcel;
import org.springframework.beans.factory.annotation.Autowired;

public class MediumParcelRule implements IParcelRule{
    private double baseCharge;

    private boolean isValid;

    public MediumParcelRule() {}

    @Autowired
    public MediumParcelRule(double baseCharge) {
        this.baseCharge = baseCharge;
    }

    @Override
    public void evaluate(Parcel parcel) {
        if (parcel.getVolume() < 2500 && parcel.getVolume() >= 1500 ) {
            parcel.setPriority(4);
            parcel.setWeightClassification("Medium Parcel");
            parcel.setCost(this.baseCharge   * parcel.getVolume());
            isValid = true;
        }
    }
    public boolean isValid() {
        return this.isValid;
    }
}
