package com.dude76.priceTracker.repository;

import com.dude76.priceTracker.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    //search based on keyword

    @Query("SELECT p FROM Product p WHERE CONCAT(p.pName) LIKE %?1%")
    public List<Product> search(String keyword);

    public boolean existsBypNameIgnoreCase(String pname);
}
