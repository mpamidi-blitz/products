package com.blitz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blitz.dao.ProductDAO;
import com.blitz.entity.Product;

@Component
public class ProductService {

	@Autowired
	private ProductDAO dao;
	
	public List<Product> getProduct(String name) {
		return dao.getProducts(name);
	}

	public Product saveProduct(Product product) {
		return dao.saveProduct(product);
	}
}
