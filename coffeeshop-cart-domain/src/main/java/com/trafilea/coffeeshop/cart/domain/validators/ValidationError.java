package com.trafilea.coffeeshop.cart.domain.validators;

public enum ValidationError {

    EMPTY_FIELD("EMPTY_FIELD", "Please enter this field"),
    INVALID_NUMBER("INVALID_NUMBER", "Invalid number format"),
    INVALID_CART_ID("INVALID_CART_ID", "There is no Cart with such id"),
    INVALID_PRODUCT_ID("INVALID_PRODUCT_ID", "There is no Product with such id");

    public final String code;
    public final String message;

    ValidationError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
