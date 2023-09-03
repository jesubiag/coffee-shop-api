package com.trafilea.coffeeshop.cart.infra.usecase;

import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import com.trafilea.coffeeshop.cart.domain.presentation.CartError;
import com.trafilea.coffeeshop.cart.domain.presentation.UpdateProductRequest;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.domain.usecase.UpdateProduct;
import com.trafilea.coffeeshop.cart.domain.validators.ValidationError;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

public class UpdateProductImpl implements UpdateProduct {

    private final CartRepository cartRepository;

    public UpdateProductImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Mono<Void> execute(UpdateProductRequest request) {
        return cartRepository.read(request.cartId())
                .flatMap(possibleCart -> possibleCart
                        .map(cart -> cart.products()
                                .stream()
                                .filter(cartProduct -> Objects.equals(cartProduct.productId(), request.productId()))
                                .findFirst()
                                .map(productForGivenId -> productForGivenId.withAmount(request.amount()))
                                .map(cart::updateProduct)
                                .map(cartRepository::update)
                                .orElse(failForProduct()))
                        .orElse(failForCart()));
    }

    private Mono<Void> failForCart() {
        return fail(ValidationError.INVALID_CART_ID, "cartId");
    }

    private Mono<Void> failForProduct() {
        return fail(ValidationError.INVALID_PRODUCT_ID, "productId");
    }

    private Mono<Void> fail(ValidationError validationError, String fieldName) {
        return Mono.error(new CartDomainException(List.of(new CartError(validationError.code, fieldName, validationError.message))));
    }

}
