package com.example.OrderPaymentOptimizer.service;

import java.math.BigDecimal;

public class Calc {
    public boolean haveEnoughMoney(BigDecimal value, BigDecimal limit){
        return (limit.compareTo(value) >= 0);
    }

    public boolean haveEnoughLoyalPoints(BigDecimal value, BigDecimal pointsLimit){
        // x = (points*100)/value
        BigDecimal solution = pointsLimit.multiply(new BigDecimal("100.00"));
        solution = solution.divide(value);

        return (solution.compareTo(new BigDecimal("10.00")) >= 0);
    }

    public BigDecimal countDiscount(BigDecimal value, int discount){
        BigDecimal percent = new BigDecimal("" + (1.0*discount/100));
        return value.multiply(percent);
    }
}
