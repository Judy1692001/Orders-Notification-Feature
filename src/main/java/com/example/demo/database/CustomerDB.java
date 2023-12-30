package com.example.demo.database;

import com.example.demo.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDB {
    private List<Customer> subscribers = new ArrayList<>();

    public void setSubscribers(List<Customer> subscribers) {
        this.subscribers = subscribers;
    }

    public List<Customer> getSubscribers() {
        return subscribers;
    }
}
