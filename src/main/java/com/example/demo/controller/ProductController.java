package com.example.demo.controller;

import com.example.demo.businesslogics.ProductBL;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    final ProductBL pbl;

    public ProductController(ProductBL pbl)
    {
        this.pbl = pbl;
    }

    @PostMapping("/add") //working
    public String AddProduct(@RequestBody Product p)
    {
        pbl.addProduct(p);

        return "Added Successfully";
    }

    @DeleteMapping("/delete") //working
    public String RemoveProduct(@RequestBody Product p)
    {
        pbl.removeProduct(p);

        return "Removed Successfully";
    }

    @GetMapping("/count")  // not working
    public int CountCategoryProducts(@RequestBody String Category)
    {
        return pbl.countOfRemainingItemsPerCategory(Category);
    }

    @GetMapping("/display")  // working
    public List <Product> DisplayProducts()
    {
        return pbl.getInv();
    }
}
