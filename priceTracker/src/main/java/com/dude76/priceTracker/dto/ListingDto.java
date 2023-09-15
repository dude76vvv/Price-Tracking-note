package com.dude76.priceTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingDto {

    @NotEmpty(message = "Platform cannot be empty")
    @Size(min = 3, max = 50)
    private String platform;

    @NotEmpty(message = "Seller cannot be empty")
    @Size(min = 3, max = 50)
    private String seller;

    @NotEmpty(message = "Price cannot be empty")
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999.99")
    private double price;

}
