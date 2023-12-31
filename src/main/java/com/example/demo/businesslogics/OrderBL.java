package com.example.demo.businesslogics;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.demo.database.CustomerDB.subscribers;
import static com.example.demo.database.OrderHistory.orders;
import static com.example.demo.database.ProductDB.warehouse;

@Service
public class OrderBL {

    public void addOrder(Order o)
    {
        orders.add(o);
    }

    public void removeOrder(Order o)
    {
        orders.remove(o);
    }

    public List <Order> getOrderHistory()
    {
        return orders;
    }

    public double calcTotalAmount(Order o)
    {
        double amount = 0.0;

        for(Product p : o.getPurchasedProducts())
        {
            amount += p.getPrice();
        }

        return amount;
    }

    public boolean checkBalance(Customer c, Order o)
    {
        return c.getBalance() >= calcTotalAmount(o);
    }

    public void deductBalance(Customer c, Order simpleOrder)
    {
        c.setBalance(c.getBalance() - calcTotalAmount(simpleOrder));
    }

    public void deductShippingFees(Customer cust, Order o)
    {
        cust.setBalance(cust.getBalance() - o.getShippingFees());
    }

    public void updateInventory(Order order)
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
    public void returnBalance(Customer c, Order simpleOrder)
    {
        c.setBalance(c.getBalance() + calcTotalAmount(simpleOrder));
    }
    public void returnShippingFees(Customer cust, Order o)
    {
        cust.setBalance(cust.getBalance() + o.getShippingFees());
    }
    public void returnToInventory(Order order)
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
            if(cust == c)
                return true;
        }

        return false;
    }

    public Order createSimpleOrder(Order simpleOrder)
    {
        if(checkRegistration(simpleOrder.getCustomer()))
        {
            if(checkBalance(simpleOrder.getCustomer(), simpleOrder))
            {
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

    public boolean checkLocation(List <Order> compoundOrder)
    {
        for(int i=0; i<compoundOrder.size()-1; i++)
        {
            for(int j=i+1; j<compoundOrder.size(); j++)
            {
                if(!(compoundOrder.get(i).getCustomer().getLocation().equals(compoundOrder.get(j).getCustomer().getLocation())))
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

    public List <Order> createCompoundOrder(List <Customer> customers, List <Order> compoundOrder)
    {
        for(Customer cust : customers)
        {
            if(!checkRegistration(cust))
                return null;

        }

        if(!checkIfExists(customers, orders))
        {
            return null;
        }

        if(checkLocation(compoundOrder))
        {
            for(Customer c : customers)
            {
                for(Order o : compoundOrder)
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
            for (Order o:compoundOrder){
                o.setPlacementTime(LocalDateTime.now());
                o.setStatus(Status.PROCESSING);
            }
            orders.addAll(compoundOrder);

            for(Customer cust : customers)
            {
                for(Order o : compoundOrder)
                {
                    if(o.getCustomer() == cust)
                    {
                        deductBalance(cust, o);

                        break;
                    }
                }
            }

            return compoundOrder;
        }

        return null;
    }

    public boolean shipSimpleOrder(Customer c, Order o)
    {
        if(checkRegistration(c))
        {
            if(orders.contains(o))
            {
                deductShippingFees(c, o);
                o.setShippingTime(LocalDateTime.now());
                o.setStatus(Status.SHIPPED);
                updateInventory(o);

                return true;
            }

            return false;
        }

        return false;
    }

    public boolean shipCompoundOrder(List <Customer> customers, List <Order> o)
    {
        for(Customer cust : customers)
        {
            if(!checkRegistration(cust))
                return false;
        }

        for(Order ord : o)
        {
            if(!orders.contains(ord))
                return false;
        }

        double fees = o.get(0).getShippingFees() / customers.size();

        for(Customer cust : customers)
        {
            cust.setBalance(cust.getBalance() - fees);
        }

        for(Order ord : o)
        {
            updateInventory(ord);
            ord.setShippingTime(LocalDateTime.now());
            ord.setStatus(Status.SHIPPED);
        }

        return true;
    }

    public boolean cancelSimpleOrderShipping(Customer c, Order o) {
        if(checkRegistration(c))
        {
            if(orders.contains(o) && o.getStatus() == Status.SHIPPED) {
                LocalDateTime cancelDeadline = o.getShippingTime().plusHours(24);
                //return product & fee
                if(LocalDateTime.now().isBefore(cancelDeadline)){
                    returnShippingFees(c, o);
                    returnToInventory(o);
                    o.setStatus(Status.PROCESSING);
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    public boolean cancelCompoundOrderShipping(List <Customer> customers, List <Order> o) {
        for(Customer cust : customers)
        {
            if(!checkRegistration(cust))
                return false;
        }
        for(Order ord : o) {
            LocalDateTime cancelDeadline = ord.getShippingTime().plusHours(24);
            if(!orders.contains(ord) && ord.getStatus() != Status.SHIPPED)
                return false;
            if(!LocalDateTime.now().isBefore(cancelDeadline))
                return false;
        }
        double fees = o.get(0).getShippingFees() / customers.size();

        for(Customer cust : customers) {
            //fee returned
            cust.setBalance(cust.getBalance() + fees);
        }
        for(Order ord : o) {
            returnToInventory(ord);
            ord.setStatus(Status.PROCESSING);
        }

        return true;
    }
    public List <Order> cancelallCompoundOrder(List <Customer> customers, List <Order> compoundOrder)
    {
        for(Customer cust : customers)
        {
            if(!checkRegistration(cust))
                return null;

        }

        if(!checkIfExists(customers, orders))
        {
            return null;
        }

        if(checkLocation(compoundOrder))
        {
            for(Customer c : customers)
            {
                for(Order o : compoundOrder)
                {
                    if(o.getCustomer() == c)
                    {
                        break;
                    }
                }
            }
            for(Customer cust : customers) {
                for(Order o : compoundOrder) {
                    if(o.getCustomer() == cust) {
                        if(o.getStatus() == Status.SHIPPED)
                            cancelSimpleOrderShipping(cust,o);
                        if(o.getStatus()== Status.PROCESSING) {
                            LocalDateTime cancelDeadline = o.getPlacementTime().plusDays(7);
                            if(LocalDateTime.now().isBefore(cancelDeadline)){
                                returnBalance(cust, o);
                                o.setStatus(Status.CANCELED);
                            }
                        }
                        break;
                    }
                }
            }

            return compoundOrder;
        }

        return null;
    }

    public Order cancelallSimpleOrder(Customer c, Order simpleOrder) {
        if(checkRegistration(c)) {
            if(orders.contains(simpleOrder)) {
                if(simpleOrder.getStatus() == Status.SHIPPED)
                    cancelSimpleOrderShipping(c,simpleOrder);
                if(simpleOrder.getStatus()== Status.PROCESSING) {
                    LocalDateTime cancelDeadline = simpleOrder.getPlacementTime().plusDays(7);
                    if(LocalDateTime.now().isBefore(cancelDeadline)){
                        returnBalance(c, simpleOrder);
                        simpleOrder.setStatus(Status.CANCELED);
                        return simpleOrder;
                    }
                }
            }
        }

        return null;
    }
}