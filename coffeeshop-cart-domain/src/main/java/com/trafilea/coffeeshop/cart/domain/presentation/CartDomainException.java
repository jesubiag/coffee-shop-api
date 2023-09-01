package com.trafilea.coffeeshop.cart.domain.presentation;

import java.util.List;

public class CartDomainException extends RuntimeException {

    public final List<CartError> errors;

    public CartDomainException(List<CartError> errors) {
        this.errors = errors;
    }
}
