package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrder extends Order{



    public List<Product> purchasedProducts = new ArrayList<>();

    public SimpleOrder(int orderID, Customer customer, double shippingFees, List<Product> purchasedProducts) {
        super(orderID, customer, shippingFees);
        this.purchasedProducts = purchasedProducts;
    }

    @Override
    public double calculateTotalPrice() {
        double total = 0;
        for (Product product : purchasedProducts) {
            total += product.getPrice();
        }
        return total + this.getShippingFees();
    }

    public List<Product> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<Product> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }
}
