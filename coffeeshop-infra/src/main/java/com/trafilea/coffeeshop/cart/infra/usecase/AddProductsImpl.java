package com.trafilea.coffeeshop.cart.infra.usecase;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import com.trafilea.coffeeshop.cart.domain.presentation.CartError;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.domain.usecase.AddProducts;
import com.trafilea.coffeeshop.cart.domain.validators.ValidationError;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public class AddProductsImpl implements AddProducts {

    private final CartRepository cartRepository;

    public AddProductsImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Mono<Void> execute(AddProductsRequest request) {
        return cartRepository.read(request.cartId())
                .flatMap(cart -> addProductsOrFail(cart, request.products()));
    }

    private Mono<Void> addProductsOrFail(Optional<Cart> possibleCart, List<Product> products) {
        return possibleCart
                .map(cart -> addProducts(cart, products))
                .orElse(fail());
    }

    private Mono<Void> addProducts(Cart cart, List<Product> products) {
        final var cartWithNewProducts = cart.addProducts(products);
        return cartRepository.update(cartWithNewProducts);
    }

    private Mono<Void> fail() {
        final var validationError = ValidationError.INVALID_CART_NUMBER;
        return Mono.error(new CartDomainException(List.of(new CartError(validationError.code, "id", validationError.message))));
    }

}
