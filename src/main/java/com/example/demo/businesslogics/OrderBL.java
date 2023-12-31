package com.example.demo.businesslogics;

import com.example.demo.entity.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static com.example.demo.database.CustomerDB.subscribers;
import static com.example.demo.database.OrderHistory.orders;
import static com.example.demo.database.ProductDB.warehouse;

@Service
public class OrderBL {

    public void addOrder(Order o)
    {
        orders.add(o);
        o.getCustomer().placeOrder(o);
    }

    public void removeOrder(Order o)
    {
        orders.remove(o);
        o.getCustomer().removeOrder(o);
    }

    public List <Order> getOrderHistory()
    {
        return orders;
    }

    public double calcTotalAmount(SimpleOrder o)
    {
        double amount = 0.0;

        for(Product p : o.getPurchasedProducts())
        {
            amount += p.getPrice();
        }
        System.out.println("Total order amount " + amount + " " + o.getPurchasedProducts());
        return amount;
    }

    public boolean checkBalance(Customer c, SimpleOrder o)
    {
        return c.getBalance() >= calcTotalAmount(o);
    }

    public void deductBalance(Customer c, SimpleOrder simpleOrder)
    {
        c.setBalance(c.getBalance() - calcTotalAmount(simpleOrder));
    }

    public void deductShippingFees(Customer cust, Order o)
    {
        cust.setBalance(cust.getBalance() - o.getShippingFees());
    }

    public void updateInventory(SimpleOrder order)
    {
        for(Product p : order.getPurchasedProducts())
        {
            for(Product pr : warehouse)
            {
                if(p == pr)
                {
                    pr.setRemainingParts(pr.getRemainingParts() - 1);

                    break;
                }
            }
        }
    }
    public void returnBalance(Customer c, SimpleOrder simpleOrder)
    {
        c.setBalance(c.getBalance() + calcTotalAmount(simpleOrder));
    }
    public void returnShippingFees(Customer cust, Order o)
    {
        cust.setBalance(cust.getBalance() + o.getShippingFees());
    }
    public void returnToInventory(SimpleOrder order)
    {
        for(Product p : order.getPurchasedProducts())
        {
            for(Product pr : warehouse)
            {
                if(p == pr)
                {
                    pr.setRemainingParts(pr.getRemainingParts() + 1);

                    break;
                }
            }
        }
    }

    public boolean checkRegistration(Customer c)
    {
        for(Customer cust : subscribers)
        {
            if(cust.getEmail().equals(c.getEmail()))
                return true;
        }

        return false;
    }

    public Order createSimpleOrder(SimpleOrder simpleOrder)
    {
        System.out.println("Here checking order " + simpleOrder.getOrderID());
        if(checkRegistration(simpleOrder.getCustomer()))
        {
            System.out.println("Passed check customer " + simpleOrder.getOrderID());
            if(checkBalance(simpleOrder.getCustomer(), simpleOrder))
            {
                System.out.println("Passed check balance " + simpleOrder.getOrderID());
                simpleOrder.setPlacementTime(LocalDateTime.now());
                simpleOrder.setStatus(Status.PROCESSING);
                orders.add(simpleOrder);

                deductBalance(simpleOrder.getCustomer(), simpleOrder);

                return simpleOrder;
            }

            return null;
        }

        return null;
    }

    public boolean checkLocation(List <Customer> customers)
    {
        for(int i=0; i<customers.size()-1; i++)
        {
            for(int j=i+1; j<customers.size(); j++)
            {
                if(!(customers.get(i).getLocation().equals(customers.get(j).getLocation())))
                    return false;
            }
        }

        return true;
    }

    public boolean checkIfExists(List <Customer> customers, List <Order> orders)
    {
        for(Customer cust : customers)
        {
            boolean flag = false;

            for(Order ord : orders)
            {
                if(cust == ord.getCustomer())
                {
                    flag = true;

                    break;
                }
            }

            if(!flag)
                return false;
        }

        return true;
    }

    public boolean checkShippingFees(List <Order> orders)
    {
        for(int i=0; i<orders.size()-1; i++)
        {
            for(int j=i+1; j<orders.size(); j++)
            {
                if(!(orders.get(i).getShippingFees() == orders.get(j).getShippingFees()))
                    return false;
            }
        }

        return true;
    }

    public CompoundOrder createCompoundOrder(List <SimpleOrder> simpleOrders)
    {
        LinkedList <Customer> customers = new LinkedList<>();
        for(Order order : simpleOrders)
        {
            if(!checkRegistration(order.getCustomer()))
                return null;
            customers.add(order.getCustomer());
        }


        if(checkLocation(customers))
        {
            for(Customer c : customers)
            {
                for(SimpleOrder o : simpleOrders)
                {
                    if(o.getCustomer() == c)
                    {
                        if(!checkBalance(c, o))
                        {
                            return null;
                        }

                        break;
                    }
                }
            }

            CompoundOrder compoundOrder = new CompoundOrder(orders.size() + 1);

            for (SimpleOrder o:simpleOrders){
                o.setPlacementTime(LocalDateTime.now());
                o.setStatus(Status.PROCESSING);
                compoundOrder.addCustomerOrder(o.getCustomer(), o);
            }
            orders.add(compoundOrder);
            return compoundOrder;
        }

        return null;
    }

    public boolean shipSimpleOrder(SimpleOrder o)
    {
        if(checkRegistration(o.getCustomer()))
        {
            if(orders.contains(o))
            {
                deductShippingFees(o.getCustomer(), o);
                o.setShippingTime(LocalDateTime.now());
                o.setStatus(Status.SHIPPED);
                updateInventory(o);

                return true;
            }

            return false;
        }

        return false;
    }

    public boolean shipCompoundOrder(List <SimpleOrder> o)
    {
//        for(Customer cust : customers)
//        {
//            if(!checkRegistration(cust))
//                return false;
//        }
//
//        for(Order ord : o)
//        {
//            if(!orders.contains(ord))
//                return false;
//        }
//
//        double fees = o.get(0).getShippingFees() / customers.size();
//
//        for(Customer cust : customers)
//        {
//            cust.setBalance(cust.getBalance() - fees);
//        }
//
//        for(SimpleOrder ord : o)
//        {
//            updateInventory(ord);
//            ord.setShippingTime(LocalDateTime.now());
//            ord.setStatus(Status.SHIPPED);
//        }

        return true;
    }

    public boolean cancelSimpleOrderShipping(SimpleOrder o) {
        if(checkRegistration(o.getCustomer()))
        {
            if(orders.contains(o) && o.getStatus() == Status.SHIPPED) {
                LocalDateTime cancelDeadline = o.getShippingTime().plusHours(24);
                //return product & fee
                if(LocalDateTime.now().isBefore(cancelDeadline)){
                    returnShippingFees(o.getCustomer(), o);
                    returnToInventory(o);
                    o.setStatus(Status.PROCESSING);
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    public boolean cancelCompoundOrderShipping(List <SimpleOrder> o) {
//        for(Customer cust : customers)
//        {
//            if(!checkRegistration(cust))
//                return false;
//        }
//        for(Order ord : o) {
//            LocalDateTime cancelDeadline = ord.getShippingTime().plusHours(24);
//            if(!orders.contains(ord) && ord.getStatus() != Status.SHIPPED)
//                return false;
//            if(!LocalDateTime.now().isBefore(cancelDeadline))
//                return false;
//        }
//        double fees = o.get(0).getShippingFees() / customers.size();
//
//        for(Customer cust : customers) {
//            //fee returned
//            cust.setBalance(cust.getBalance() + fees);
//        }
//        for(SimpleOrder ord : o) {
//            returnToInventory(ord);
//            ord.setStatus(Status.PROCESSING);
//        }

        return true;
    }
    public List <Order> cancelallCompoundOrder(List <SimpleOrder> compoundOrder)
    {
//        for(Customer cust : customers)
//        {
//            if(!checkRegistration(cust))
//                return null;
//
//        }
//
//        if(!checkIfExists(customers, orders))
//        {
//            return null;
//        }
//
//        if(checkLocation(compoundOrder))
//        {
//            for(Customer c : customers)
//            {
//                for(Order o : compoundOrder)
//                {
//                    if(o.getCustomer() == c)
//                    {
//                        break;
//                    }
//                }
//            }
//            for(Customer cust : customers) {
//                for(Order o : compoundOrder) {
//                    if(o.getCustomer() == cust) {
//                        if(o.getStatus() == Status.SHIPPED)
//                            cancelSimpleOrderShipping(cust,o);
//                        if(o.getStatus()== Status.PROCESSING) {
//                            LocalDateTime cancelDeadline = o.getPlacementTime().plusDays(7);
//                            if(LocalDateTime.now().isBefore(cancelDeadline)){
//                                returnBalance(cust, o);
//                                o.setStatus(Status.CANCELED);
//                            }
//                        }
//                        break;
//                    }
//                }
//            }
//
//            return compoundOrder;
//        }

        return null;
    }

    public Order cancelallSimpleOrder(SimpleOrder simpleOrder) {
        if(checkRegistration(simpleOrder.getCustomer())) {
            if(orders.contains(simpleOrder)) {
                if(simpleOrder.getStatus() == Status.SHIPPED)
                    cancelSimpleOrderShipping(simpleOrder);
                if(simpleOrder.getStatus()== Status.PROCESSING) {
                    LocalDateTime cancelDeadline = simpleOrder.getPlacementTime().plusDays(7);
                    if(LocalDateTime.now().isBefore(cancelDeadline)){
                        returnBalance(simpleOrder.getCustomer(), simpleOrder);
                        simpleOrder.setStatus(Status.CANCELED);
                        return simpleOrder;
                    }
                }
            }
        }

        return null;
    }
}