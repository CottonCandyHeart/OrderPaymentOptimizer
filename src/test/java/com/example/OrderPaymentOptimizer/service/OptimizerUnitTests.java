package com.example.OrderPaymentOptimizer.service;

import com.example.OrderPaymentOptimizer.model.DiscountOption;
import com.example.OrderPaymentOptimizer.model.Order;
import com.example.OrderPaymentOptimizer.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OptimizerUnitTests {
    private Optimizer optimizer;

    @BeforeEach
    void setUp(){
        List<Order> orders = new ArrayList<>();
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        optimizer = new Optimizer(orders,paymentMethods);
    }

    @Test
    void shouldGetDiscountOpiton(){

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

    @Test
    void shouldCountDiscountsForOrders() {

        Order order1 = new Order();
        order1.setId("ORD1");
        order1.setValue(new BigDecimal("100.00"));
        order1.setPromotions(Arrays.asList("CARD1", "PUNKTY"));

        List<Order> orders = new ArrayList<>();
        orders.add(order1);

        PaymentMethod card1 = new PaymentMethod();
        card1.setId("CARD1");
        card1.setDiscount(10);
        card1.setLimit(new BigDecimal("500.00"));

        PaymentMethod points = new PaymentMethod();
        points.setId("PUNKTY");
        points.setDiscount(0);
        points.setLimit(new BigDecimal("20.00"));

        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(card1);
        paymentMethods.add(points);
        Optimizer optimizer = new Optimizer(orders, paymentMethods);

        optimizer.countDiscountsForOrders();

        List<DiscountOption> actualDiscounts = optimizer
                .sortListDesc(optimizer
                        .getPartialPayment(order1, points.getLimit(),
                                new ArrayList<>(
                                        List.of(optimizer.getDiscountOpiton(new BigDecimal("100.00"), 10, "CARD1"))
                                )
                        )
                );

        assertThat(actualDiscounts.size()).isEqualTo(2);
        assertThat(actualDiscounts.get(0).getPaymentMethodId()).isIn("MIX", "CARD1");
    }


}
