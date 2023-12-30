package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private Customer customer;
    private double shippingFees;
    public static List <Product> purchasedProducts = new ArrayList<>();

    public Order(int orderID, Customer customer, double shippingFees, List <Product> purchasedProducts)
    {
        this.orderID = orderID;
        this.customer = customer;
        this.shippingFees = shippingFees;
        Order.purchasedProducts = purchasedProducts;
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
}
