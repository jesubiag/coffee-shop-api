package com.trafilea.coffeeshop.app.rest.update;

import com.trafilea.coffeeshop.app.rest.CoffeeShopHandler;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class CartUpdateHandler extends CoffeeShopHandler {

    private final CartApi cartApi;

    public CartUpdateHandler(CartApi cartApi) {
        this.cartApi = cartApi;
    }

    public Mono<ServerResponse> addProducts(String cartId, AddProductsJsonRequest request) {
        final var products = request.products()
                .stream()
                .map(productJson -> new Product(null, productJson.name(), Product.Category.valueOf(productJson.category()), productJson.price()))
                .toList();

        final var addProductsRequest = AddProductsRequest.factory(cartId, products);

        return cartApi.addProducts(addProductsRequest)
                .then(ServerResponse.noContent().build())
                .onErrorResume(this::badRequestFromCartDomainException);
    }

}
