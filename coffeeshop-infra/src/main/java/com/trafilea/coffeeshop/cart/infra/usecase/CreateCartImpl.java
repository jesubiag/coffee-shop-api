package com.trafilea.coffeeshop.cart.infra.usecase;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateCart;
import reactor.core.publisher.Mono;

public class CreateCartImpl implements CreateCart {

    private final CartRepository cartRepository;

    public CreateCartImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Mono<String> execute(Long userId) {
        // TODO: should check user exists?
        final var newCart = Cart.empty(userId);
        return cartRepository.create(newCart);
    }

}
