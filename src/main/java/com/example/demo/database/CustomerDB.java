package com.example.demo.database;

import com.example.demo.entity.Customer;

import java.util.*;

public class CustomerDB {
    private List <Customer> subscribers;

    public CustomerDB()
    {
        this.subscribers = new ArrayList<>();
    }

    public void setSubscribers(List<Customer> subscribers) {
        this.subscribers = subscribers;
    }

    public List<Customer> getSubscribers() {
        if (subscribers == null)
        {
            subscribers = new ArrayList<>();
        }

        return subscribers;
    }
}
