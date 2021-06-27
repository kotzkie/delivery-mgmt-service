package com.mynt.logistics.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Slf4j
public class ParcelMetricsDTO {
    @NotNull(message = "Weight is required")
    private Double weight;
    @NotNull(message = "Height is required")
    private Double height;
    @NotNull(message = "Width is required")
    private Double width;
    @NotNull(message = "Length is required")
    private Double length;
    @Pattern(regexp = "^[A-Za-z0-9_@.-]\\S*$",  message = "Voucher had invalid characters.")
    private String voucher;

    public double getWeight() {
        return weight;
    }

    @SneakyThrows
    public void setWeight(double weight) {
        if (weight == 0.0) {
            log.error("Weight input is either 0.0 or blank");
            throw new IllegalArgumentException("Weight should not be 0.0");
        }
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height == 0.0) {
            throw new IllegalArgumentException("Height should not be 0.0");
        }
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width == 0.0) {
            throw new IllegalArgumentException("Width should not be 0.0");
        }
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        if (length == 0.0) {
            throw new IllegalArgumentException("Length should not be 0.0");
        }
        this.length = length;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParcelMetricsDTO that = (ParcelMetricsDTO) o;

        if (Double.compare(that.weight, weight) != 0) return false;
        if (Double.compare(that.height, height) != 0) return false;
        if (Double.compare(that.width, width) != 0) return false;
        return Double.compare(that.length, length) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(weight);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(width);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(length);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ParcelMetricsDTO{" + "weight=" + weight + ", height=" + height + ", width=" + width + ", length=" + length + ", voucher='" + voucher + '\'' + '}';
    }
}
