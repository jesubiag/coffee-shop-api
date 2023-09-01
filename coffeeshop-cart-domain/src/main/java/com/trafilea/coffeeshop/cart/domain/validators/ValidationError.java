package com.trafilea.coffeeshop.cart.domain.validators;

public enum ValidationError {

    EMPTY_FIELD("EMPTY_FIELD", "Please enter this field"),
    NO_VALUE("NO_VALUE", "Invalid number format"),
    INVALID_CART_NUMBER("INVALID_CART_NUMBER", "There is no cart with such id");

    public final String code;
    public final String message;

    ValidationError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
