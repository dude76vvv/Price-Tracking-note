package com.dude76.priceTracker.controller;

import com.dude76.priceTracker.dto.ListingDto;
import com.dude76.priceTracker.entity.Listing;
import com.dude76.priceTracker.entity.Product;
import com.dude76.priceTracker.exceptions.ProductNotFoundException;
import com.dude76.priceTracker.repository.ListingRepository;
import com.dude76.priceTracker.repository.ProductRepository;
import com.dude76.priceTracker.service.ListingService;
import com.dude76.priceTracker.service.impl.ListingServiceImpl;
import com.dude76.priceTracker.service.impl.ProductServiceImpl;
import com.dude76.priceTracker.utilities.MinMax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Controller
public class ListingController {

    @Autowired
    private ListingServiceImpl listingService;

    @Autowired
    private ProductServiceImpl productService;

    //displaying listing of the that product
    //id refer to product id
    @GetMapping("/listing/{id}")
    public String viewListing(Model model, @PathVariable("id") int id, @Param("sortField") String sortField, @Param("sortDir") String sortDir) {

        List<Listing> productListing = listingService.listingByProductId(id,sortField,sortDir);

        //get ProductName
        String pName = listingService.getProductName(productListing);

        //get the min and price from the list of listing
        MinMax min_max_object = listingService.getMinMax(productListing);

        //get all listing inside
        model.addAttribute("productListing",productListing);
        model.addAttribute("productName",pName);
        model.addAttribute("productMinMax",min_max_object);
        model.addAttribute("productId",id);

        if (sortDir !=null){

            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        }

        else{
            model.addAttribute("reverseSortDir", "desc");

        }

        return "itemListing";
    }


    //navigate to the form route
    @GetMapping("/listing/{id}/add")
    public String viewAddListing(Model model, @PathVariable("id") int id) throws ProductNotFoundException {

        Product currProduct = productService.getById(id);

        model.addAttribute("listingDto",new ListingDto());
        model.addAttribute("currProduct",currProduct);

        return "newListing_form";
    }


    //add listing based on current product
    //the add listing button will pass the product id
    @PostMapping("/listing/{id}/add")
    public String addNewProductListing(@PathVariable("id") int id, @Valid @ModelAttribute("listingDto") ListingDto listingDto, RedirectAttributes ra) throws ProductNotFoundException {

        //fetch the product using id
        Product p =productService.getById(id);

        //fetch listing that has seller and platform null and that product id,
        //remove it since it was a dummy
        Listing dummy = listingService.getDummyListing(id);

        if(dummy !=null){

            listingService.delListingByItem(dummy);
        }

        //create the listing object from dto
        Listing newListing= Listing.builder()
                .listedProduct(p)
                .platform(listingDto.getPlatform())
                .seller(listingDto.getSeller())
                .price(listingDto.getPrice())
                .lastSeen(new Date())
                .build();

        //save to db
        listingService.saveListing(newListing);

        //redirect back
        ra.addFlashAttribute("successMessage","listing successfully added!");


        return "redirect:/listing/"+ id;
    }

    //navigate and pass the listing information
    @GetMapping("/listing/{id}/edit")
    public String editProduct(Model model,@PathVariable("id") int productId,  @Param("listingId") int listingId) throws ProductNotFoundException {

        Product currProduct = productService.getById(productId);
        Listing currListing  = listingService.getListing(listingId);

        model.addAttribute("currProduct",currProduct);
        model.addAttribute("currListing",currListing);

        return "editListing_form";

    }

    @PostMapping("/listing/{id}/edit")
    public String editProduct(@PathVariable("id") int productId,@ModelAttribute("currListing") Listing listingDto,  @Param("listingId") int listingId ) throws ProductNotFoundException {

        Listing currListing  = listingService.getListing(listingId);

        currListing.setPlatform(listingDto.getPlatform());
        currListing.setSeller(listingDto.getSeller());
        currListing.setPrice(listingDto.getPrice());

        currListing.setLastSeen(new Date());

        listingService.saveListing(currListing);

        return "redirect:/listing/"+ productId;
    }




    //delete
    //id is based on listing
    @GetMapping("/listing/{id}/delete")
    public String delProduct(@PathVariable("id") int productId, @Param("listingId") int listingId) throws ProductNotFoundException {

        //redirect to product page if no listing
        int count =listingService.delListing(productId,listingId);

        return count>0?"redirect:/listing/"+ productId:"redirect:/products";
    }


}
