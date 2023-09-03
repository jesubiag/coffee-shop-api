package com.trafilea.coffeeshop.app.rest.update;

import com.trafilea.coffeeshop.app.rest.CoffeeShopHandler;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import com.trafilea.coffeeshop.cart.domain.presentation.UpdateProductRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class CartUpdateHandler extends CoffeeShopHandler {

    private final CartApi cartApi;

    public CartUpdateHandler(CartApi cartApi) {
        this.cartApi = cartApi;
    }

    public Mono<ServerResponse> addProducts(String cartId, AddProductsJsonRequest jsonRequest) {
        final var products = jsonRequest.products()
                .stream()
                .map(productJson -> new Product(null, productJson.name(), Product.Category.valueOf(productJson.category()), productJson.price()))
                .toList();

        final var request = AddProductsRequest.factory(cartId, products);

        return cartApi.addProducts(request)
                .then(ServerResponse.noContent().build())
                .onErrorResume(this::badRequestFromCartDomainException);
    }

    public Mono<ServerResponse> updateProduct(String cartId, String productId, UpdateProductJsonRequest jsonRequest) {
        final var request = UpdateProductRequest.factory(cartId, productId, jsonRequest.amount());

        return cartApi.updateProduct(request)
                .then(ServerResponse.noContent().build())
                .onErrorResume(this::badRequestFromCartDomainException);
    }
}
