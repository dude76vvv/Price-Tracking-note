package com.dude76.priceTracker.service;

import com.dude76.priceTracker.entity.Product;
import com.dude76.priceTracker.exceptions.ProductNotFoundException;

import java.util.List;

public interface productService {
    //methods available to use - add,delete,get,get_all
     List<Product> listAll(String keyword);
     Product getById(int id) throws ProductNotFoundException;
     void add(Product product);

     void deleteById(int id) throws ProductNotFoundException;

     boolean productExistByName(String pName);
}
