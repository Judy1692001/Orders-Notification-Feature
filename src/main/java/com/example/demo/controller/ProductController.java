package com.example.demo.controller;

import com.example.demo.businesslogics.ProductBL;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.businesslogics.ProductBL.getInv;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    final ProductBL pbl;

    public ProductController(ProductBL pbl)
    {
        this.pbl = pbl;
    }

    @PostMapping("/add")
    public String AddProduct(@RequestBody Product p)  //working
    {
        pbl.addProduct(p);

        return "Added Successfully";
    }

    @DeleteMapping("/delete") // not working
    public String RemoveProduct(@RequestBody Product p)
    {
        pbl.removeProduct(p);

        return "Removed Successfully";
    }

    @GetMapping("/count/{Category}") //working
    public int CountCategoryProducts(@PathVariable String Category)
    {
        return pbl.countOfRemainingItemsPerCategory(Category);
    }

    @GetMapping("/display") //working
    public static List <Product> DisplayProducts()
    {
        return getInv();
    } //not updated with changes
}
