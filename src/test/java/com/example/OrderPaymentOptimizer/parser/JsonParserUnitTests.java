package com.example.OrderPaymentOptimizer.parser;

import com.example.OrderPaymentOptimizer.model.Order;
import com.example.OrderPaymentOptimizer.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParserUnitTests {
    private JsonReader jsonReader;

    @BeforeEach
    void setUp(){
        jsonReader = new JsonReader();
    }

    @Test
    void shouldParseOrdersValidFile() throws IOException {
        String ordersPath = "src/test/resources/ordersTest.json";
        List<Order> orders = jsonReader.parseOrders(ordersPath);

        assertNotNull(orders, "Order list shouldn't be null");
        assertFalse(orders.isEmpty(), "Order list should't be empty");

        BigDecimal expected = new BigDecimal("100.00");

        Order firstOrder = orders.get(0);
        assertEquals("ORDER1", firstOrder.getId(), "Order ID should match expected");
        assertEquals(0, firstOrder.getValue().compareTo(expected),"Order value should match expected");

    }

    @Test
    void shouldParseOrdersInvalidFile() {
        String invalidOrdersPath = "src/test/resources/invalidOrdersTest.json";

        assertThrows(IOException.class, () -> {
            jsonReader.parseOrders(invalidOrdersPath);
        }, "Should throw exception");
    }

    @Test
    void shouldParsePaymentMethodsValidFile() throws IOException {
        String paymentMethodsPath = "src/test/resources/paymentmethodsTest.json";
        List<PaymentMethod> paymentMethods = jsonReader.parsePaymentMethods(paymentMethodsPath);

        assertNotNull(paymentMethods, "Payment Methods list shouldn't be null");
        assertFalse(paymentMethods.isEmpty(), "Payment Methods should't be empty");

        PaymentMethod firstPaymentMethod = paymentMethods.get(0);
        assertEquals("PUNKTY", firstPaymentMethod.getId(), "Payment Method ID should match expected");
        assertEquals(15, firstPaymentMethod.getDiscount(),"Payment Method value should match expected");

    }

    @Test
    void shouldParsePaymentMethodsInvalidFile() {
        String invalidOrdersPath = "src/test/resources/invalidPaymentMethodsTest.json";

        assertThrows(IOException.class, () -> {
            jsonReader.parseOrders(invalidOrdersPath);
        }, "Should throw exception");
    }
}
