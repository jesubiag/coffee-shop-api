package com.trafilea.coffeeshop.cart.domain.api;

import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import com.trafilea.coffeeshop.cart.domain.presentation.CreateOrderResponse;
import com.trafilea.coffeeshop.cart.domain.presentation.UpdateProductRequest;
import com.trafilea.coffeeshop.cart.domain.usecase.AddProducts;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateCart;
import com.trafilea.coffeeshop.cart.domain.usecase.UpdateProduct;
import reactor.core.publisher.Mono;

public class CartApi {

    private final CreateCart createCart;
    private final AddProducts addProducts;
    private final UpdateProduct updateProduct;

    public CartApi(CreateCart createCart, AddProducts addProducts, UpdateProduct updateProduct) {
        this.createCart = createCart;
        this.addProducts = addProducts;
        this.updateProduct = updateProduct;
    }

    public Mono<String> createCart(Long userId) {
        return createCart.execute(userId);
    }

    public Mono<Void> addProducts(AddProductsRequest request) {
        return addProducts.execute(request);
    }

    public Mono<Void> updateProduct(UpdateProductRequest request) {
        return updateProduct.execute(request);
    }

    public Mono<CreateOrderResponse> createOrder(String cartId) {
        return null;
    }
}
