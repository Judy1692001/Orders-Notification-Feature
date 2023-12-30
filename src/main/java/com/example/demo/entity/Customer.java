package com.example.demo.entity;

public class Customer {

    private int customerID;
    private String name;
    private double balance;
    private String location;

    public Customer(int customerID, String name, double balance, String location)
    {
        this.customerID = customerID;
        this.name = name;
        this.balance = balance;
        this.location = location;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
