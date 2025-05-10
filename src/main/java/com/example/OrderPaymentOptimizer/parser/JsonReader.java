package com.example.OrderPaymentOptimizer.parser;

import com.example.OrderPaymentOptimizer.model.Order;
import com.example.OrderPaymentOptimizer.model.PaymentMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {

    public List<Order> parseOrders(String ordersPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(new File(ordersPath), new TypeReference<>(){});
    }

    public List<PaymentMethod> parsePaymentMethods(String paymentMethodsPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(new File(paymentMethodsPath), new TypeReference<>() {});
    }


}
