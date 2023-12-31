package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Order {
    private int orderID;

    private int compoundOrderId;
    private Customer customer;
    private double shippingFees;
    private Status status;
    private LocalDateTime PlacementTime;
    private LocalDateTime ShippingTime;

    public Order(int orderID, Customer customer, double shippingFees)
    {
        this.orderID = orderID;
        this.customer = customer;
        this.shippingFees = shippingFees;
    }

    public Order() {

    }

    public abstract double calculateTotalPrice();

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setShippingFees(double shippingFees) {
        this.shippingFees = shippingFees;
    }

    public double getShippingFees() {
        return shippingFees;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getPlacementTime() {
        return PlacementTime;
    }

    public LocalDateTime getShippingTime() {
        return ShippingTime;
    }

    public void setShippingTime(LocalDateTime shippingTime) {
        ShippingTime = shippingTime;
    }

    public void setPlacementTime(LocalDateTime placementTime) {
        PlacementTime = placementTime;
    }

    public int getCompoundOrderId() {
        return compoundOrderId;
    }

    public void setCompoundOrderId(int compoundOrderId) {
        this.compoundOrderId = compoundOrderId;
    }
}
