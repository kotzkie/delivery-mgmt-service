package com.mynt.logistics.domain.parcel.rules;

import com.mynt.logistics.domain.Parcel;

public interface IParcelRule {
    boolean isValid();
    void evaluate(Parcel parcel);
}
