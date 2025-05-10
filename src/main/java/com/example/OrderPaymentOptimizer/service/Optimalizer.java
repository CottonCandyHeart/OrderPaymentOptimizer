package com.example.OrderPaymentOptimizer.service;

import com.example.OrderPaymentOptimizer.model.Order;
import com.example.OrderPaymentOptimizer.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Optimalizer {
    private List<Order> orders;
    private List<PaymentMethod> paymentMethods;

    public Optimalizer(List<Order> orders, List<PaymentMethod> paymentMethods){
        this.orders = orders;
        this.paymentMethods = paymentMethods;
    }

    public void sortOrdersByDiscount(){
        List<Order> sortedOrders = new ArrayList<>();

        for (Order o : this.orders){
            BigDecimal discount;
        }
    }
/*
    public String bestDiscount(Order order, List<PaymentMethod> paymentMethods){
        List<String> promotions = order.getPromotions();
        BigDecimal value = order.getValue();
        String bestDiscoundId;

        if (paymentMethods.getFirst().getLimit().compareTo(value) >= 0){
            bestDiscoundId = "PUNKTY";
        } else {
            BigDecimal bestDiscountVal = new BigDecimal(0);

            for (PaymentMethod pm : paymentMethods){
                if (promotions.contains(pm.getId())){
                    if (pm.getLimit().compareTo(value) > 0){

                    }
                }
            }
        }


    }*/

}
