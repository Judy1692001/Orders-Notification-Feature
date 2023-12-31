package com.example.demo.controller;

import com.example.demo.businesslogics.OrderBL;
import com.example.demo.entity.CompoundOrder;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.SimpleOrder;
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
    public Order PlaceSimpleOrder(@RequestBody SimpleOrder simpleOrder)
    {
        return obl.createSimpleOrder(simpleOrder);
    }

    @PostMapping("/createcompoundorder")
    public CompoundOrder PlaceCompoundOrder(@RequestBody List<SimpleOrder> compoundOrder)
    {
        return obl.createCompoundOrder(compoundOrder);
    }

    @PutMapping("/shipsimpleorder")
    public String ShipSimpleOrder(@RequestBody SimpleOrder simpleOrder)
    {
        obl.shipSimpleOrder(simpleOrder);

        return "Order Shipped Successfully!";
    }

    @PutMapping("/shipcompoundorder")
    public String ShipCompoundOrder(@RequestBody List <SimpleOrder> compoundOrder)
    {
        obl.shipCompoundOrder(compoundOrder);

        return "Order Shipped Successfully!";
    }
    @PutMapping("/cancelsimpleorderShipping")
    public String CancelSimpleOrderShipping(@RequestBody SimpleOrder simpleOrder)
    {
        obl.cancelSimpleOrderShipping(simpleOrder);

        return "Shipping canceled Successfully!";
    }

    @PutMapping("/cancelcompoundorderShipping")
    public String CancelCompoundOrderShipping(@RequestBody List <SimpleOrder> compoundOrder)
    {
        obl.cancelCompoundOrderShipping(compoundOrder);

        return "Shipping canceled Successfully!";
    }
    @PutMapping("/cancelsimpleorder")
    public String CancelSimpleOrder(@RequestBody SimpleOrder simpleOrder)
    {
        obl.cancelallSimpleOrder(simpleOrder);

        return "Order canceled Successfully!";
    }

    @PutMapping("/cancelcompoundorder")
    public String CancelCompoundOrder(@RequestBody List <SimpleOrder> compoundOrder)
    {
        obl.cancelallCompoundOrder(compoundOrder);

        return "Order canceled Successfully!";
    }
}