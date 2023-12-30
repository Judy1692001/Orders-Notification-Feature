package com.example.demo.businesslogics;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import org.springframework.stereotype.Service;

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

    public boolean checkRegistration(Customer c)
    {
        for(Customer cust : subscribers)
        {
            if(cust == c)
                return true;
        }

        return false;
    }

    public Order createSimpleOrder(Customer c, Order simpleOrder)
    {
        if(checkRegistration(c))
        {
            if(checkBalance(c, simpleOrder))
            {
                orders.add(simpleOrder);

                deductBalance(c, simpleOrder);

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
        }

        return true;
    }
}
