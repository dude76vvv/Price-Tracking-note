package com.dude76.priceTracker.service;

import com.dude76.priceTracker.entity.Listing;
import com.dude76.priceTracker.entity.Product;
import com.dude76.priceTracker.entity.ProductPriceRange;
import com.dude76.priceTracker.utilities.MinMax;
import java.util.List;

public interface ListingService {

    //get all items with price range
    List<ProductPriceRange> listItemsPriceRangeSearch(String keyword);

    //add new listing
    void addDefaultListing(Product product);

    List<Listing> listingByProductId(int pid,String sortField,String sortDir);

    //get min and max price from List<Listing>
    MinMax getMinMax(List<Listing> lis);


    //get pName from List<listing>
    String getProductName(List<Listing> lis);

    void saveListing(Listing listing);

    int delListing(int productId,int listingId);


    Listing getListing(int listingId);

    Listing getDummyListing(int productId);

    void delListingByItem(Listing item);

}
