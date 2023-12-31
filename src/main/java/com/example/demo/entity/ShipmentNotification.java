package com.example.demo.entity;

import java.util.LinkedList;
import java.util.stream.Collectors;

import static com.example.demo.database.OrderHistory.orders;

public class ShipmentNotification implements Notification{

    private String customerName;
    private int numDays=3;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void setOrderId(int orderId) {

    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    private double fees;
    @Override
    public String sendNotification(Customer c) {
        LinkedList<Order> customerOrders = orders.stream()
                .filter(order -> order.getCustomer().getUserName().equals(c.getUserName()))
                .collect(Collectors.toCollection(LinkedList::new));

        // Assuming 'numDays' is defined in this scope
        // Check if the customer has any orders
        if (customerOrders.isEmpty()) {
            return "Dear " + c.getUserName() + ", you have no orders pending.";
        }

        // Assuming you want to refer to the first order for the notification
        Order firstOrder = customerOrders.get(0);

        return "Dear " + c.getUserName() + ", your order will be delivered within " + numDays + " days and shipping fees is " + firstOrder.getShippingFees();
    }
}
