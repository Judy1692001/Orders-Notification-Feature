package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private Customer customer;
    private double shippingFees;
    public static List <Product> purchasedProducts = new ArrayList<>();
    private Status status;
    private LocalDateTime PlacementTime;
    private LocalDateTime ShippingTime;

    public Order(int orderID, Customer customer, double shippingFees, List <Product> purchasedProducts)
    {
        this.orderID = orderID;
        this.customer = customer;
        this.shippingFees = shippingFees;
        Order.purchasedProducts = purchasedProducts;
        this.status = Status.PROCESSING;
    }

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

    public void setPurchasedProducts(List<Product> purchasedProducts) {
        Order.purchasedProducts = purchasedProducts;
    }

    public List<Product> getPurchasedProducts() {
        return purchasedProducts;
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
}
