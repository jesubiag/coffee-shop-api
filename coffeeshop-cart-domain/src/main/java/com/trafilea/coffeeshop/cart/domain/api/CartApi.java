package com.trafilea.coffeeshop.cart.domain.api;

import com.trafilea.coffeeshop.cart.domain.model.Order;
import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import com.trafilea.coffeeshop.cart.domain.presentation.CreateOrderResponse;
import com.trafilea.coffeeshop.cart.domain.presentation.UpdateProductRequest;
import com.trafilea.coffeeshop.cart.domain.usecase.AddProducts;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateCart;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateOrder;
import com.trafilea.coffeeshop.cart.domain.usecase.UpdateProduct;
import reactor.core.publisher.Mono;

public class CartApi {

    private final CreateCart createCart;
    private final AddProducts addProducts;
    private final UpdateProduct updateProduct;
    private final CreateOrder createOder;

    public CartApi(CreateCart createCart, AddProducts addProducts, UpdateProduct updateProduct, CreateOrder createOder) {
        this.createCart = createCart;
        this.addProducts = addProducts;
        this.updateProduct = updateProduct;
        this.createOder = createOder;
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
        return createOder.execute(cartId).map(this::buildCreateOrderResponse);
    }

    private CreateOrderResponse buildCreateOrderResponse(Order order) {
        return new CreateOrderResponse(order.products(), order.discounts(), order.shipping(), order.total().doubleValue());
    }
}
