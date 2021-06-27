package com.mynt.logistics.domain.parcel.rules;

import com.mynt.logistics.domain.Parcel;
import com.mynt.logistics.domain.parcel.rules.IParcelRule;

public class RejectParcelRule implements IParcelRule {
    private boolean isValid;
    @Override
    public void evaluate(Parcel parcel) {
        if (parcel.getWeight() > 50) {
            parcel.setPriority(1);
            parcel.setWeightClassification("Reject");
        }
    }

    public boolean isValid() {
        return this.isValid;
    }
}
