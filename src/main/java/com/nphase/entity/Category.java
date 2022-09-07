package com.nphase.entity;

import java.math.BigDecimal;

public enum Category {
    FOOD(3, new BigDecimal("0.9")),
    DRINK(3, new BigDecimal("0.9"));

    private final int countForDiscount;

    private final BigDecimal discount;

    Category(int countForDiscount, BigDecimal discount) {
        this.countForDiscount = countForDiscount;
        this.discount = discount;
    }

    public int getCountForDiscount() {
        return countForDiscount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
