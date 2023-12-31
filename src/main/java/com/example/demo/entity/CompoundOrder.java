package com.example.demo.entity;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.database.CustomerDB.subscribers;
import static com.example.demo.database.OrderHistory.orders;

public class CompoundOrder extends Order {

    private Map<Customer, SimpleOrder> customerOrders;

    public CompoundOrder(int orderID) {
        super();
        this.setOrderID(orderID);
        this.customerOrders = new HashMap<>();
    }

    public void addCustomerOrder(Customer customer, SimpleOrder order) {
        customerOrders.put(customer, order);
    }

    @Override
    public double calculateTotalPrice() {
        double total = 0;
        for (SimpleOrder order : customerOrders.values()) {
            total += order.calculateTotalPrice();
        }
        return total;
    }

    public Map<Customer, SimpleOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(Map<Customer, SimpleOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }
}

