package com.example.demo.businesslogics;

import com.example.demo.database.CustomerDB;
import com.example.demo.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerBL {

    private CustomerDB subscribers;

    public void addCustomer(Customer c)
    {
        subscribers.getSubscribers().add(c);
    }

    public boolean createAccount(Customer c)
    {
        for(Customer cust : subscribers.getSubscribers())
        {
            if(cust.getUserName().equals(c.getUserName()))
            {
                if(cust.getEmail().equals(c.getEmail()))
                    return false;
            }
            else if(cust.getEmail().equals(c.getEmail()))
                return false;
        }

        subscribers.getSubscribers().add(c);

        return true;
    }

    public Customer getCustomer(int id)
    {
        for(Customer c : subscribers.getSubscribers())
        {
            if(c.getCustomerID() == id)
                return c;
        }

        return null;
    }

    public List <Customer> getAllCustomers()
    {
        return subscribers.getSubscribers();
    }

    public void putBalance(int customerId, double Balance)
    {
        for(Customer cust : subscribers.getSubscribers())
        {
            if(cust.getCustomerID() == customerId)
            {
                cust.setBalance(cust.getBalance() + Balance);
            }
        }
    }
}
