package com.example.demo.businesslogics;

import com.example.demo.entity.Product;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static com.example.demo.database.ProductDB.warehouse;

@Service
public class ProductBL {

    public void addProduct(Product p)
    {
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
