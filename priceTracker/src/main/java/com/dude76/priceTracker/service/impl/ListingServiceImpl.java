package com.dude76.priceTracker.service.impl;

import com.dude76.priceTracker.entity.Listing;
import com.dude76.priceTracker.entity.Product;
import com.dude76.priceTracker.entity.ProductPriceRange;
import com.dude76.priceTracker.repository.ListingRepository;
import com.dude76.priceTracker.repository.ProductRepository;
import com.dude76.priceTracker.service.ListingService;
import com.dude76.priceTracker.utilities.MinMax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService
{
    @Autowired
    private ListingRepository lRepo;

    @Autowired
    private ProductRepository rRepo;

    @Override
//    search with keyword if not null
    public List<ProductPriceRange> listItemsPriceRangeSearch(String keyword) {
        if(keyword != null){
            return lRepo.searchListProductPrice(keyword);
        }
        return lRepo.listAllProductPrice();
    }

    @Override
    public void addDefaultListing(Product p) {

        Listing defaultNewListing = Listing.builder()
                .platform(null)
                .seller(null)
                .price(0)
                .lastSeen(null)
                .listedProduct(p)
                .build();

        lRepo.save(defaultNewListing);
    }

    @Override
    public List<Listing> listingByProductId(int pid, String sortField, String sortDir) {

        Sort sort = null;

        if((sortField == null) && (sortDir == null)){

            sort = Sort.by("price").ascending();
        }
        else{

            sort = sortDir.equals("asc") ? Sort.by(sortField).ascending(): Sort.by(sortField).descending();

        }


        return lRepo.findAllByListedProduct_Id(pid,sort);
    }


    @Override
    public MinMax getMinMax(List<Listing> lis) {

        List<Double> priceList= lis.stream().map(x->x.getPrice())
                .collect(Collectors.toList());

        double minValue = Collections.min(priceList);
        double maxValue = Collections.max(priceList);

        return new MinMax(minValue,maxValue);

    }

    @Override
    public String getProductName(List<Listing> lis) {

        String ProductName = null;

        if(lis.size()>0){
            ProductName = lis.get(0).getListedProduct().getPName();
        }

        return ProductName;
    }

    @Override
    public void saveListing(Listing listing) {
        lRepo.save(listing);
    }

    @Override
    public int delListing(int productId,int listingId) {

        lRepo.deleteById(listingId);

        int countListing = lRepo.countByListedProduct_Id(productId);

        if (countListing==0){

            //create dummy
            Product dummy =rRepo.findById(productId).get();
            addDefaultListing(dummy);

        }

        return countListing;
    }

    @Override
    public Listing getListing(int listingId) {

        return lRepo.findById(listingId).get();
    }

    @Override
    public Listing getDummyListing(int productId) {

        Listing dummy =null;

        List<Listing> dummyList = lRepo.findDummyListing(productId);

        if(dummyList.size() ==1){
            dummy = dummyList.get(0);
        }

        return dummy ;
    }

    @Override
    public void delListingByItem(Listing item) {
        lRepo.delete(item);
    }


}
