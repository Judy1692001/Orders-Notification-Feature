package com.example.demo.entity;

public class Customer {

    private int customerID;
    private String userName;
    private String email;
    private double balance;
    private String location;

    public Customer(int customerID, String userName, String email, double balance, String location)
    {
        this.customerID = customerID;
        this.userName = userName;
        this.email = email;
        this.balance = balance;
        this.location = location;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
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
