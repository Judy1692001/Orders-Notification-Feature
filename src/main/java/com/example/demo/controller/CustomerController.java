package com.example.demo.controller;

import com.example.demo.businesslogics.CustomerBL;
import com.example.demo.businesslogics.OrderBL;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    final CustomerBL cbl;
    @Autowired
    final OrderBL obl;

    public CustomerController(CustomerBL cbl, OrderBL obl) {
        this.cbl = cbl;
        this.obl = obl;
    }

    @PostMapping("/createaccount")
    public String CreateAccount(@RequestBody Customer c) //working
    {
        cbl.createAccount(c);

        return "Account created Successfully";
    }

    @PutMapping("/addbalance/{customerid}/{newbalance}") //working
    public String AddBalance(@PathVariable int customerid, @PathVariable double newbalance)
    {
        cbl.putBalance(customerid, newbalance);

        return "Balance updated Successfully";
    }

    @PostMapping("/order/simple") //not working
    public Order PlaceSimpleOrder(@RequestBody Customer c, @RequestBody Order simpleOrder)
    {
        return obl.createSimpleOrder(c, simpleOrder);
    }

    @PostMapping("/order/compound")
    public List <Order> PlaceCompoundOrder(@RequestBody List <Customer> customers, @RequestBody List<Order> compoundOrder)
    {
        return obl.createCompoundOrder(customers, compoundOrder);
    }

    // can ship order

    @PutMapping("/shipsimpleorder")
    public String ShipSimpleOrder(@RequestBody Customer c, @RequestBody Order simpleOrder)
    {
        obl.shipSimpleOrder(c, simpleOrder);

        return "Order Shipped Successfully!";
    }

    @PutMapping("/shipcompoundorder")
    public String ShipCompoundOrder(@RequestBody List <Customer> c, @RequestBody List <Order> compoundOrder)
    {
        obl.shipCompoundOrder(c, compoundOrder);

        return "Order Shipped Successfully!";
    }
}
