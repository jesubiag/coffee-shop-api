package com.trafilea.coffeeshop.cart.domain.api;

import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import com.trafilea.coffeeshop.cart.domain.usecase.AddProducts;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateCart;
import reactor.core.publisher.Mono;

public class CartApi {

    private final CreateCart createCart;
    private final AddProducts addProducts;

    public CartApi(CreateCart createCart, AddProducts addProducts) {
        this.createCart = createCart;
        this.addProducts = addProducts;
    }

    public Mono<String> createCart(Long userId) {
        // TODO: any validation?
        return createCart.execute(userId);
    }

    public Mono<Void> addProducts(AddProductsRequest request) {
        return addProducts.execute(request);
    }
}
