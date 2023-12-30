package com.example.demo.businesslogics;

import com.example.demo.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerBL {

    private List <Customer> subscribers = new ArrayList<>();

    public void addCustomer(Customer c)
    {
        subscribers.add(c);
    }

    public void createAccount(Customer c)
    {
        subscribers.add(c);
    }

    public List <Customer> getAllCustomers()
    {
        return subscribers;
    }

    public void putBalance(int customerId, double Balance)
    {
        for(Customer cust : subscribers)
        {
            if(cust.getCustomerID() == customerId)
            {
                cust.setBalance(cust.getBalance() + Balance);
            }
        }
    }
}
