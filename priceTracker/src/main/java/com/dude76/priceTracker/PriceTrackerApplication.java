package com.dude76.priceTracker;

import com.dude76.priceTracker.entity.Listing;
import com.dude76.priceTracker.entity.Product;
import com.dude76.priceTracker.repository.ListingRepository;
import com.dude76.priceTracker.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class PriceTrackerApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository pRepo;
	@Autowired
	private ListingRepository lRepo;

	public static void main(String[] args) {
		SpringApplication.run(PriceTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = formatter.parse("30/07/2023");
		Date date2 = formatter.parse("22/6/2023");
		Date date3 = formatter.parse("24/07/2023");
		Date date4 = formatter.parse("2/07/2023");

		//products
		Product p0 = Product.builder()
				.pName("Legend of Zelda Botw")
				.build();

		Product p1 = Product.builder()
				.pName("SFVI")
				.build();

		Product p2 = Product.builder()
				.pName("Mario kart 8 deluxe")
				.build();


		Product p3 = Product.builder()
				.pName("Sonic R")
				.build();

		Product p4 = Product.builder()
				.pName("Spiderman miles morales")
				.build();

		pRepo.save(p0);
		pRepo.save(p1);
		pRepo.save(p2);
		pRepo.save(p3);
		pRepo.save(p4);

		Listing l0 = Listing.builder()
				.platform("q10")
				.seller("abc")
				.price(52.20)
				.lastSeen(date1)
				.listedProduct(p0)
				.build();

		lRepo.save(l0);

		Listing l1 = Listing.builder()
				.platform("shopee")
				.seller("efg")
				.price(62.20)
				.lastSeen(date2)
				.listedProduct(p1)
				.build();

		lRepo.save(l1);

		Listing l2 = Listing.builder()
				.platform("amazon")
				.seller("abc")
				.price(55.50)
				.lastSeen(date3)
				.listedProduct(p2)
				.build();

		lRepo.save(l2);

		Listing l3 = Listing.builder()
				.platform("lazada")
				.seller("bestPrice")
				.price(50)
				.lastSeen(date4)
				.listedProduct(p4)
				.build();

		lRepo.save(l3);



		Listing l4 = Listing.builder()
				.platform("q10")
				.seller("xyz")
				.price(55.20)
				.lastSeen(date3)
				.listedProduct(p0)
				.build();

		lRepo.save(l4);

	}
}
