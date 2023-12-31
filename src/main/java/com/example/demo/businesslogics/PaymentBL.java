package com.example.demo.businesslogics;

import com.example.demo.entity.CompoundOrder;
import com.example.demo.entity.Order;
import com.example.demo.entity.Customer;
import com.example.demo.entity.SimpleOrder;

import java.util.*;

import static com.example.demo.database.OrderHistory.orders;

public class PaymentBL {

    public String handlePayment(int orderId) {
        ;
        Optional<Order> opOrder = orders.stream().filter(c -> c.getOrderID() == orderId).findFirst();
        if (opOrder.isEmpty()) {
            return "Order not found";
        }
        Order order = opOrder.get();
        if (order instanceof SimpleOrder) {
            Customer c = order.getCustomer();
            if (c.getBalance() < order.calculateTotalPrice()) {
                return "Not enough balance";
            }
            c.setBalance(c.getBalance() - order.calculateTotalPrice());
        } else if (order instanceof CompoundOrder) {
            CompoundOrder cOrder = (CompoundOrder) order;
            for (Map.Entry<Customer, SimpleOrder> entry : cOrder.getCustomerOrders().entrySet()) {
                if (entry.getKey().getBalance() < entry.getValue().calculateTotalPrice()) {
                    return "Not enough balance for customer " + entry.getKey().getCustomerID();
                }
                entry.getKey().setBalance(entry.getKey().getBalance() - entry.getValue().calculateTotalPrice());
            }
        }
        return "Payment done successfully";
    }

    public Map<Customer, SimpleOrder> findCustomers(Order order) {
        if (order instanceof SimpleOrder) {
            Map<Customer, SimpleOrder> result = new HashMap<>();
            result.put(order.getCustomer(), (SimpleOrder) order);
            return result;
        } else if (order instanceof  CompoundOrder) {
            return ((CompoundOrder) order).getCustomerOrders();
        }
        return null;
    }
}
