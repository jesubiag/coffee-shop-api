package com.trafilea.coffeeshop.cart.domain.presentation;

import com.trafilea.coffeeshop.cart.domain.validators.CartIdValidator;
import com.trafilea.coffeeshop.cart.domain.validators.ProductAmountValidator;
import com.trafilea.coffeeshop.cart.domain.validators.ProductIdValidator;

import java.util.ArrayList;
import java.util.List;

public record UpdateProductRequest(String cartId, String productId, int amount) {

    public static UpdateProductRequest factory(String cartId, String productId, int amount) {
        validate(cartId, productId, amount);
        return new UpdateProductRequest(cartId, productId, amount);
    }

    private static void validate(String cartId, String productId, int amount) {
        List<CartError> errors = new ArrayList<>();

        errors.addAll(CartIdValidator.validate(cartId)
                .stream()
                .map(vr -> new CartError(vr.code, "id", vr.message))
                .toList());
        errors.addAll(ProductIdValidator.validate(productId)
                .stream()
                .map(vr -> new CartError(vr.code, "productId", vr.message))
                .toList());
        errors.addAll(ProductAmountValidator.validate(amount)
                .stream()
                .map(vr -> new CartError(vr.code, "amount", vr.message))
                .toList());

        if (!errors.isEmpty()) {
            throw new CartDomainException(errors);
        }
    }

}
