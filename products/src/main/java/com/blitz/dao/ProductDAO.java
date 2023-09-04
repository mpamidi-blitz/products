package com.blitz.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blitz.entity.Product;
import com.blitz.repository.ProductRepository;

@Component
public class ProductDAO {

	@Autowired
	private ProductRepository repository;
	
	public List<Product> getProducts(String name) {
		return repository.findByName(name);
	}

	public Product saveProduct(Product product) {
		return repository.save(product);
	}
	
}
