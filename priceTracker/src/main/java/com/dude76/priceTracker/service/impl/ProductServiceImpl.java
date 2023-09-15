package com.dude76.priceTracker.service.impl;
import com.dude76.priceTracker.entity.Product;
import com.dude76.priceTracker.exceptions.ProductNotFoundException;
import com.dude76.priceTracker.repository.ProductRepository;
import com.dude76.priceTracker.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements productService {

    @Autowired
    private ProductRepository repo;


    @Override
    public List<Product> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    @Override
    public Product getById(int id) throws ProductNotFoundException {
        Optional<Product> pItem = repo.findById(id);
        if (pItem.isPresent()) {
            return pItem.get();
        }
        else {
            throw new ProductNotFoundException("product of id:" + id + " not found, unable to delete");
        }

    }

    @Override
    public void add(Product product) {

        repo.save(product);
    }

    @Override
    public void deleteById(int id) throws ProductNotFoundException {

        if(!repo.findById(id).isPresent()){
            throw new ProductNotFoundException("product of id: " + id + " not found,unable to delete");
        }
        repo.deleteById(id);

    }

    @Override
    public boolean productExistByName(String pName) {

        return repo.existsBypNameIgnoreCase(pName);
    }

}
