package com.dude76.priceTracker.repository;

import com.dude76.priceTracker.entity.Listing;
import com.dude76.priceTracker.entity.Product;
import com.dude76.priceTracker.entity.ProductPriceRange;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer> {

    //to display price range
    //group by productId,find the min and max, return the object

    @Query("select new com.dude76.priceTracker.entity.ProductPriceRange(p ,max(l.price), min(l.price)) from Product p join Listing l on p.id = l.listedProduct.id group by p")
    List<ProductPriceRange> listAllProductPrice();

    //based on search bar
    @Query("select new com.dude76.priceTracker.entity.ProductPriceRange(p ,max(l.price), min(l.price)) from Product p join Listing l on p.id = l.listedProduct.id WHERE CONCAT(p.pName) LIKE %?1% group by p")
    List<ProductPriceRange> searchListProductPrice(String keyword);

    List<Listing> findAllByListedProduct_Id(int pid, Sort sort);

    int countByListedProduct_Id(int pid);

    //find product that has seller null and platform null for a product
    @Query("select l from com.dude76.priceTracker.entity.Listing l where l.seller is Null and l.platform is Null and l.listedProduct.id =?1" )
    List<Listing> findDummyListing(int pid);


}
