package com.mynt.logistics.domain;

import com.mynt.logistics.domain.parcel.rules.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RuleTest {

    @Test
    @DisplayName("Test creation of HeavyParcelRule  object")
    public void testHeavyParcelRuleCreate() {
        assertNotNull(new HeaveyParcelRule());
    }

    @Test
    @DisplayName("Test creation of MediumParcelRule  object")
    public void testMediumParcelRuleCreate() {
        assertNotNull(new MediumParcelRule());
    }

    @Test
    @DisplayName("Test creation of SmallParcelRule  object")
    public void testSmallParcelRuleCreate() {
        assertNotNull(new SmallParcelRule());
    }

    @Test
    @DisplayName("Test creation of LargeParcelRule  object")
    public void testLargeParcelRuleCreate() {
        assertNotNull(new LargestParcelRule());
    }

    @Test
    @DisplayName("Test creation of RejectParcelRule  object")
    public void testRejectParcelRuleCreate() {
        assertNotNull(new RejectParcelRule());
    }
}
