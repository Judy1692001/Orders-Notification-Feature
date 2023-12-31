package com.example.demo.controller;

import com.example.demo.businesslogics.OrderBL;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    final OrderBL obl;

    public OrderController(OrderBL obl) {
        this.obl = obl;
    }

    @PostMapping("/createsimpleorder")
    public Order PlaceSimpleOrder( Customer c,  Order simpleOrder)
    {
        return obl.createSimpleOrder(c, simpleOrder);
    }

    @PostMapping("/createcompoundorder")
    public List<Order> PlaceCompoundOrder(@RequestBody List <Customer> customers, @RequestBody List<Order> compoundOrder)
    {
        return obl.createCompoundOrder(customers, compoundOrder);
    }

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
    @PutMapping("/cancelsimpleorderShipping")
    public String CancelSimpleOrderShipping(@RequestBody Customer c, @RequestBody Order simpleOrder)
    {
        obl.cancelSimpleOrderShipping(c, simpleOrder);

        return "Shipping canceled Successfully!";
    }

    @PutMapping("/cancelcompoundorderShipping")
    public String CancelCompoundOrderShipping(@RequestBody List <Customer> c, @RequestBody List <Order> compoundOrder)
    {
        obl.cancelCompoundOrderShipping(c, compoundOrder);

        return "Shipping canceled Successfully!";
    }
    @PutMapping("/cancelsimpleorder")
    public String CancelSimpleOrder(@RequestBody Customer c, @RequestBody Order simpleOrder)
    {
        obl.cancelallSimpleOrder(c, simpleOrder);

        return "Order canceled Successfully!";
    }

    @PutMapping("/cancelcompoundorder")
    public String CancelCompoundOrder(@RequestBody List <Customer> c, @RequestBody List <Order> compoundOrder)
    {
        obl.cancelallCompoundOrder(c, compoundOrder);

        return "Order canceled Successfully!";
    }
}