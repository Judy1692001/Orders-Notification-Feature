package com.example.demo.businesslogics;

import com.example.demo.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.database.ProductDB.warehouse;

@Service
public class ProductBL {

    public void addProduct(Product p)
    {
        for(Product pr : warehouse)
        {
            if(pr.getSerialNo() == p.getSerialNo())
            {
                pr.setRemainingParts(pr.getRemainingParts() + p.getRemainingParts());

                return;
            }
        }

        warehouse.add(p);

    }

    public boolean removeProduct(int serialnumber)
    {
        for(Product p : warehouse)
        {
            if(p.getSerialNo() == serialnumber)
            {
                warehouse.remove(p);

                return true;
            }
        }

        return false;
    }

    public static List <Product> getInv() {
        return warehouse;
    }

    public int countOfRemainingItemsPerCategory(String Category)
    {
        int count = 0;

        for(Product pr : warehouse)
        {
            if(pr.getCategory().equals(Category))
            {
                count += pr.getRemainingParts();
            }
        }

        return count;
    }
}
