package com.ssotter.sscatalog.tests;

import java.time.Instant;

import com.ssotter.sscatalog.dto.ProductDTO;
import com.ssotter.sscatalog.entities.Category;
import com.ssotter.sscatalog.entities.Product;

public class Factory {

	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		
		Product produc = createProduct();
		return new ProductDTO(produc, produc.getCategories()); 
	}
	
	public static Category createCategory() {
		
		return new Category(1L, "Eletronics");
	}
}
