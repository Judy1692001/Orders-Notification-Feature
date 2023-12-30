package com.example.demo.businesslogics;

import com.example.demo.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.database.ProductDB.warehouse;

@Service
public class ProductBL {

    public void addProduct(Product p)
    {
        warehouse.add(p);
    }

    public void removeProduct(Product p)
    {
        for(Product pr : warehouse)
        {
            if(pr == p)
                warehouse.remove(pr);
        }
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
