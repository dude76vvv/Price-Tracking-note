package com.dude76.priceTracker.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductPriceRange {

    private Product product;
    private double maxPrice;
    private double minPrice;
}
