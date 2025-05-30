package com.example.OrderPaymentOptimizer.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calc {
    public boolean haveEnoughMoney(BigDecimal value, BigDecimal limit){
        return (limit.compareTo(value) >= 0);
    }

    public boolean haveEnoughLoyalPoints(BigDecimal value, BigDecimal pointsLimit){
        BigDecimal solution = pointsLimit.multiply(new BigDecimal("100.00"));
        solution = solution.divide(value, 2, RoundingMode.HALF_UP);
        return (solution.compareTo(new BigDecimal("10.00")) >= 0);
    }

    public BigDecimal countDiscount(BigDecimal value, int discount){
        BigDecimal percent = new BigDecimal("" + (1.0*discount/100));
        return value.multiply(percent);
    }
}
