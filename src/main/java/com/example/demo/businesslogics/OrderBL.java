package com.example.demo.businesslogics;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderBL {
    private List <Order> orderHistory = new ArrayList<>();
    private List <Product> warehouse = new ArrayList<>();

    public void addOrder(Order o)
    {
        orderHistory.add(o);
    }

    public void removeOrder(Order o)
    {
        orderHistory.remove(o);
    }

    public List <Order> getOrderHistory()
    {
        return orderHistory;
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
        c.setBalance(c.getBalance() - calcTotalAmount(simpleOrder) - simpleOrder.getShippingFees());
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

    public Order createSimpleOrder(Customer c, Order simpleOrder)
    {
        if(checkBalance(c, simpleOrder))
        {
            orderHistory.add(simpleOrder);

            return simpleOrder;
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

    public List <Order> createCompoundOrder(List <Customer> customers, List <Order> compoundOrder)
    {
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

            orderHistory.addAll(compoundOrder);

            return compoundOrder;
        }

        return null;
    }

    public void shipSimpleOrder(Customer c, Order o)
    {
        deductBalance(c, o);

        updateInventory(o);
    }

    public void shipCompoundOrder(List <Customer> customers, List <Order> orders)
    {
        // how to handle shipping fees part?
    }
}
