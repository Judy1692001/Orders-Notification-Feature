package com.example.demo.controller;

import com.example.demo.businesslogics.CustomerBL;
import com.example.demo.businesslogics.OrderBL;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    final CustomerBL cbl;

    public CustomerController(CustomerBL cbl) {
        this.cbl = cbl;
    }

    @GetMapping("/retrievecustomer/{id}")
    public Customer getCustomer(@PathVariable int id)
    {
        return cbl.getCustomer(id);
    }

    @PostMapping("/createaccount")
    public List <Product> CreateAccount(@RequestBody Customer c)
    {
        return cbl.createAccount(c);
    }

    @PutMapping("/addbalance/{customerid}/{newbalance}")
    public String AddBalance(@PathVariable int customerid, @PathVariable double newbalance)
    {
        if(cbl.putBalance(customerid, newbalance))
            return "Balance updated Successfully";

        return "Customer doesn't exist";
    }
}
