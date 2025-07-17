package com.shop.demo.exceptions;

public class InvalidCouponException extends RuntimeException {
	
    public InvalidCouponException(String message) {
        super(message);
    }
    
}
