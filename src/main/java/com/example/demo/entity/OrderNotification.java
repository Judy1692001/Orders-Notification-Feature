package com.example.demo.entity;

import java.util.LinkedList;
import java.util.stream.Collectors;

import static com.example.demo.database.OrderHistory.orders;

public class OrderNotification implements Notification{

    private String customerName;
    private int OrderId;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    @Override
    public void setFees(double fees) {

    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    private String product;
    @Override
    public String sendNotification(Customer c) {
        LinkedList<Order> customerOrders = orders.stream()
                .filter(order -> order.getCustomer().getUserName().equals(c.getUserName()))
                .collect(Collectors.toCollection(LinkedList::new));

        if (customerOrders.isEmpty()) {
            return "No orders found for " + c.getUserName();
        }

        Order lastOrder = customerOrders.getLast();

        return "Dear " + c.getUserName() + ", your booking of the order " + lastOrder.getOrderID() + " is confirmed. Thanks for using our store";
    }
}
