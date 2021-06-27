package com.mynt.logistics.domain.parcel.rules;

import com.mynt.logistics.domain.Parcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class HeaveyParcelRule implements IParcelRule{

    private double baseCharge;


    private boolean isValid;

    public HeaveyParcelRule() {}

    @Autowired
    public HeaveyParcelRule(double baseCharge) {
        this.baseCharge = baseCharge;
    }

    @Override
    public void evaluate(Parcel parcel) {
        if (parcel.getWeight() >10 && parcel.getWeight() <= 50) {
            parcel.setPriority(2);
            parcel.setWeightClassification("Heavy Parcel");
            parcel.setCost(this.baseCharge * parcel.getWeight());
            isValid = true;
        }
    }


    public boolean isValid() {
        return this.isValid;
    }
}
