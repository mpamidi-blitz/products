package com.blitz.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.blitz.dto.Coupon;
import com.blitz.entity.Product;
import com.blitz.service.ProductService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/productsapi/")
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClient;

	@GetMapping(value = "/test")
	public String testProduct() {
		return "This is from testProduct() method in ProductController class";
	}

	@GetMapping(value = "/products/{name}")
	public List<Product> getProduct(@PathVariable(name = "name") String name) {
		return service.getProduct(name);
	}

	@PostMapping(value = "/products")
	public Product createProduct(@RequestBody Product product) {
		System.out.println("**********************");
		System.out.println("Input Product: " + product.toString());
		String url = "http://localhost:9090/couponsapi/coupons/";
		HttpHeaders headers = new HttpHeaders();

		String authHeader = "mpamidi" + ":" + "mpamidi";
		byte[] authBytes = authHeader.getBytes();
		String auth = Base64.getEncoder().encodeToString(authBytes);
		headers.set("authorization", auth);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		Coupon coupon = restTemplate.getForObject(url + "TEST1", Coupon.class);
		System.out.println("Coupon: " + coupon);
		
		Mono<Coupon> bodyToMono = WebClient.create()
					.get()
					.uri(url+"TEST1")
					.retrieve()
					.bodyToMono(Coupon.class);
		
		bodyToMono.subscribe(c -> {System.out.println("Inside"); c.toString();});
		System.out.println("bodyToMono: " +bodyToMono);
		
		System.out.println("**********************");		
		return service.saveProduct(product);
	}
}
