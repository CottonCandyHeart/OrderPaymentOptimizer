package com.example.OrderPaymentOptimizer;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.OrderPaymentOptimizer.model.Order;
import com.example.OrderPaymentOptimizer.model.PaymentMethod;
import com.example.OrderPaymentOptimizer.parser.JsonReader;

import java.io.IOException;
import java.util.List;

//@SpringBootApplication
public class OrderPaymentOptimizerApplication {

	public static void main(String[] args) {
		//SpringApplication.run(OrderPaymentOptimizerApplication.class, args);

		if (args.length < 2) {
			System.out.println("Please provide paths to orders.json and paymentmethods.json");
			System.exit(1);
		}

		String ordersPath = args[0];
		String paymentMethodsPath = args[1];

		//System.out.println("Orders path: " + ordersPath);
		//System.out.println("Payment methods path: " + paymentMethodsPath);

		JsonReader jsonReader = new JsonReader();

        try {
            List<Order> orders = jsonReader.parseOrders(ordersPath);
			List<PaymentMethod> paymentMethods = jsonReader.parsePaymentMethods(paymentMethodsPath);

			//System.out.println("Files parsed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
