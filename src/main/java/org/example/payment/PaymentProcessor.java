package org.example.payment;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.PaymentResult;

public class PaymentProcessor {
    public PaymentResult process(Order order, PaymentMethod paymentMethod){
        System.out.println(order);
        if (order.isPaid()) {
            return new PaymentResult(false, "Payment failed. Order is empty.");
        }
        if (order.getStatus() == OrderStatus.PAID){
            return new PaymentResult((false), "Payment failed. Order has already been paid.");
        }

        PaymentResult result = paymentMethod.pay(order.calculateTotal());

        if(result.isSuccessful()){
            order.markAsPaid();
        }

        return result;
    }
}

