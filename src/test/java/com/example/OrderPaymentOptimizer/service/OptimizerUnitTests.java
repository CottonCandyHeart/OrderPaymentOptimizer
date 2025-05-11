package com.example.OrderPaymentOptimizer.service;

import com.example.OrderPaymentOptimizer.model.DiscountOption;
import com.example.OrderPaymentOptimizer.model.Order;
import com.example.OrderPaymentOptimizer.model.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OptimizerUnitTests {

    @Test
    void shouldGetDiscountOpiton(){
        List<Order> orders = new ArrayList<>();
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        Optimizer optimizer = new Optimizer(orders,paymentMethods);

        BigDecimal originValue = new BigDecimal("100.00");
        int discount = 10;
        String paymentMethodId = "POINTS";

        DiscountOption expected = new DiscountOption("POINTS", new BigDecimal("100.00"), new BigDecimal("90.00"), new BigDecimal("10.000"));
        DiscountOption actual = optimizer.getDiscountOpiton(originValue,discount,paymentMethodId);

        assertThat(actual.getPaymentMethodId()).isEqualTo(expected.getPaymentMethodId());
        assertThat(actual.getOriginalValue()).isEqualByComparingTo(expected.getOriginalValue());
    }

    @Test
    void shouldSortListDesc(){
        List<Order> orders = new ArrayList<>();
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        Optimizer optimizer = new Optimizer(orders,paymentMethods);

        DiscountOption opt1 = new DiscountOption("mZysk", new BigDecimal("100.00"), new BigDecimal("90.00"), new BigDecimal("10.00"));
        DiscountOption opt2 = new DiscountOption("BosBankrut", new BigDecimal("100.00"), new BigDecimal("80.00"), new BigDecimal("20.00"));
        DiscountOption opt3 = new DiscountOption("POINTS", new BigDecimal("100.00"), new BigDecimal("70.00"), new BigDecimal("30.00"));

        List<DiscountOption> actual = new ArrayList<>();
        actual.add(opt1);
        actual.add(opt2);
        actual.add(opt3);

        List<DiscountOption> sorted = optimizer.sortListDesc(actual);

        assertEquals(new BigDecimal("30.00"), sorted.get(0).getDiscountAmount());
        assertEquals(new BigDecimal("20.00"), sorted.get(1).getDiscountAmount());
        assertEquals(new BigDecimal("10.00"), sorted.get(2).getDiscountAmount());
    }

}
