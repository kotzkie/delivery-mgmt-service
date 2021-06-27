package com.mynt.logistics.domain.parcel.rules;

import com.mynt.logistics.domain.Parcel;
import org.springframework.beans.factory.annotation.Autowired;

public class LargestParcelRule implements IParcelRule{
    private double baseCharge;

    private boolean isValid;

    public LargestParcelRule() {}

    @Autowired
    public LargestParcelRule(double baseCharge) {
        this.baseCharge = baseCharge;
    }

    @Override
    public void evaluate(Parcel parcel) {
        if (parcel.getVolume() > 2500 ) {
            parcel.setPriority(5);
            parcel.setWeightClassification("Large Parcel");
            parcel.setCost( this.baseCharge   * parcel.getVolume());
            isValid =true;
        }
    }



    public boolean isValid() {
        return this.isValid;
    }
}
