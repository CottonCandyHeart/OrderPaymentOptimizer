package com.example.OrderPaymentOptimizer.service;

import com.example.OrderPaymentOptimizer.parser.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CalcUnitTests {
    private Calc calc;

    @BeforeEach
    void setUp(){
        calc = new Calc();
    }

    @Test
    void shouldReturnTrueHaveEnoughMoney() {
        BigDecimal value = new BigDecimal("100.00");
        BigDecimal limit = new BigDecimal("150.00");

        assertTrue(calc.haveEnoughMoney(value, limit));
    }

    @Test
    void shouldReturnFalseHaveEnoughMoney(){
        BigDecimal value = new BigDecimal("150.00");
        BigDecimal limit = new BigDecimal("100.00");

        assertFalse(calc.haveEnoughMoney(value, limit));
    }

    @Test
    void shouldReturnTrueLoyalPointsEquals10percent(){
        BigDecimal value = new BigDecimal("100.00");
        BigDecimal limit = new BigDecimal("10.00");

        assertTrue(calc.haveEnoughLoyalPoints(value, limit));
    }

    @Test
    void shouldReturnTrueLoyalPointsGreaterThan10percent(){
        BigDecimal value = new BigDecimal("100.00");
        BigDecimal limit = new BigDecimal("15.00");

        assertTrue(calc.haveEnoughLoyalPoints(value, limit));
    }

    @Test
    void shouldReturnFalseLoyalPointsLowerThan10percent(){
        BigDecimal value = new BigDecimal("100.00");
        BigDecimal limit = new BigDecimal("5.00");

        assertFalse(calc.haveEnoughLoyalPoints(value, limit));
    }

    @Test
    void shouldReturnDiscount10(){
        BigDecimal value = new BigDecimal("100.00");
        int discount = 10;

        assertEquals(new BigDecimal("10.000"), calc.countDiscount(value,discount));
    }

    @Test
    void shouldReturnDiscount31(){
        BigDecimal value = new BigDecimal("210.00");
        int discount = 15;

        assertEquals(new BigDecimal("31.5000"), calc.countDiscount(value,discount));
    }

    @Test
    void shouldReturnDiscount0(){
        BigDecimal value = new BigDecimal("210.00");
        int discount = 0;

        assertEquals(new BigDecimal("0.000"), calc.countDiscount(value,discount));
    }
}
