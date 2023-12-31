package com.trafilea.coffeeshop.cart.domain.presentation;

import com.trafilea.coffeeshop.cart.domain.model.CartProduct;
import com.trafilea.coffeeshop.cart.domain.validators.CartIdValidator;
import com.trafilea.coffeeshop.cart.domain.validators.ProductValidator;

import java.util.ArrayList;
import java.util.List;

public record AddProductsRequest(String cartId, List<CartProduct> products) {

    public static AddProductsRequest factory(String cartId, List<CartProduct> products) {
        validate(cartId, products);
        return new AddProductsRequest(cartId, products);
    }

    private static void validate(String cartId, List<CartProduct> products) {
        List<CartError> errors = new ArrayList<>();

        errors.addAll(CartIdValidator.validate(cartId)
                .stream()
                .map(vr -> new CartError(vr.code, "id", vr.message))
                .toList());
        errors.addAll(ProductValidator.validate(products)
                .stream()
                .map(vr -> new CartError(vr.code, "products", vr.message))
                .toList());

        if (!errors.isEmpty()) {
            throw new CartDomainException(errors);
        }
    }

}
