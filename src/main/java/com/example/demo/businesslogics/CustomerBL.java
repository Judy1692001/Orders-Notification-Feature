package com.example.demo.businesslogics;

import com.example.demo.database.CustomerDB;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.database.CustomerDB.subscribers;
import static com.example.demo.database.ProductDB.warehouse;


@Service
public class CustomerBL {

//    public void addCustomer(Customer c)
//    {
//
//    }

    public List <Product> createAccount(Customer c)
    {
        if(subscribers.isEmpty())
        {
            subscribers.add(c);

            return warehouse;
        }

        for(Customer cust : subscribers)
        {
            if(cust.getUserName().equals(c.getUserName()))
            {
                if(cust.getEmail().equals(c.getEmail()))
                    return null;
            }
            else if(cust.getEmail().equals(c.getEmail()))
                return null;
        }

        subscribers.add(c);

        return warehouse;
    }

    public Customer getCustomer(int id)
    {
        for(Customer c : subscribers)
        {
            if(c.getCustomerID() == id)
                return c;
        }

        return null;
    }

    public List <Customer> getAllCustomers()
    {
        return subscribers;
    }

    public boolean putBalance(int customerId, double Balance)
    {
        for(Customer cust : subscribers)
        {
            if(cust.getCustomerID() == customerId)
            {
                cust.setBalance(cust.getBalance() + Balance);

                return true;
            }
        }

        return false;
    }
}
