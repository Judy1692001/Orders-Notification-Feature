package com.example.demo.controller;

import com.example.demo.businesslogics.NotificationBL;
import com.example.demo.businesslogics.PaymentBL;
import java.util.Map;
import java.util.Optional;

import com.example.demo.entity.CompoundOrder;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.SimpleOrder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import static com.example.demo.database.OrderHistory.orders;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

  private final PaymentBL payment;
  NotificationBL Nbsl = new NotificationBL();

  public PaymentController() {
    this.payment = new PaymentBL();
  }

  @GetMapping("/hlo")
  public String gg() {
    return "hello";
  }

  //    public String doPayment(double o, double c){
  //        return payment.placeOrder(o,c);
  //    }
  @PutMapping("/dopayment/{orderId}")
  public String doPayment(@PathVariable int orderId) {
    String message = payment.handlePayment(orderId);
    Optional<Order> opOrder = orders.stream().filter(c -> c.getOrderID() == orderId).findFirst();
    if (opOrder.isEmpty()) {
      return "Order not found";
    }
    Order order = opOrder.get();
    if (message.equals("Payment done successfully")) {
      String msgemail = "";
      String msgsms = "";
      for (Map.Entry<Customer, SimpleOrder> entry : payment
        .findCustomers(order)
        .entrySet()) {
        msgemail += Nbsl.doNotificationEmail("order", entry.getKey()) + " ";
        msgsms += Nbsl.doNotificationSMS("order", entry.getKey()) + " ";
      }
      return message + "\n" + msgemail + "\n" + msgsms + "\n";
    }

    return message;
  }
}
