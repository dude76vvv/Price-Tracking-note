package com.dude76.priceTracker.controller;


import com.dude76.priceTracker.entity.Listing;
import com.dude76.priceTracker.entity.Product;
import com.dude76.priceTracker.entity.ProductPriceRange;
import com.dude76.priceTracker.exceptions.ProductNotFoundException;
import com.dude76.priceTracker.service.impl.ListingServiceImpl;
import com.dude76.priceTracker.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class NavController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ListingServiceImpl listingService;

    //home page
    @GetMapping("/")
    public String showHome(){
        return "index";
    }

    //list all products
    @GetMapping("/products")
    public String viewProducts(Model model,@Param("keyword") String keyword){

        //fetch all and pass into view using model
        List<ProductPriceRange> ProductPriceLis =listingService.listItemsPriceRangeSearch(keyword);
        model.addAttribute("ProductPriceLis",ProductPriceLis);
        model.addAttribute("keyword", keyword);
        model.addAttribute("product", new Product());

        return "products";
    }

    @PostMapping("/products")
    public String addProduct(@Valid @ModelAttribute("product") Product product , BindingResult bindingResult,  RedirectAttributes ra){

        if(bindingResult.hasErrors()){
            ra.addFlashAttribute("ErrorMessage","Input is not correct!");
            return "redirect:/products";
        }

        //check if already in database based on product name
        boolean productNameExist = productService.productExistByName(product.getPName());

        if(productNameExist){
            ra.addFlashAttribute("ErrorMessage","Product already exist!");
            return "redirect:/products";
        }

        System.out.println("product does not exist yay");

        productService.add(product);

        //creating a default listing when new product is added
        listingService.addDefaultListing(product);
        ra.addFlashAttribute("successMessage","Product successfully added!");

        return "redirect:/products";
    }

    //link is always GET request,only form have POST request
    //@DeleteMapping("/products/{id}/delete")

    @GetMapping("/products/{id}/delete")
    public String delProduct(@PathVariable("id") int id) throws ProductNotFoundException {


        productService.deleteById(id);
        return "redirect:/products";
    }


    @GetMapping("/test")
    public void test(){

//        listingService.getItemPriceRange();

    }

}
