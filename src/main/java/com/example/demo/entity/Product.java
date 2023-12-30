package com.example.demo.entity;

public class Product {

    private int serialNo;
    private String name;
    private String vendor;
    private String category;
    private int remainingParts;
    private double price;

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setRemainingParts(int remainingParts) {
        this.remainingParts = remainingParts;
    }

    public int getRemainingParts() {
        return remainingParts;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
