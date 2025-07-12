package com.shop.demo.exceptions;

public class ProductNotFoundException extends RuntimeException {
	
    public ProductNotFoundException(String message) {
        super(message);
    }
    
}
