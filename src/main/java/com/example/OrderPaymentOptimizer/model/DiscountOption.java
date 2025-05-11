package com.example.OrderPaymentOptimizer.model;

import java.math.BigDecimal;

public class DiscountOption {
    private String paymentMethodId;
    private BigDecimal originalValue;
    private BigDecimal discountedValue;
    private BigDecimal discountAmount;

    public DiscountOption(String paymentMethodId, BigDecimal originalValue, BigDecimal discountedValue, BigDecimal discountAmount){
        this.paymentMethodId = paymentMethodId;
        this.originalValue = originalValue;
        this.discountedValue = discountedValue;
        this.discountAmount = discountAmount;
    }

    public String getPaymentMethodId() { return paymentMethodId; }
    public BigDecimal getOriginalValue() { return originalValue; }
    public BigDecimal getDiscountedValue() { return discountedValue; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
}
