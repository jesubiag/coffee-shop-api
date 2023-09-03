package com.trafilea.coffeeshop.app.rest.create;

import com.trafilea.coffeeshop.app.rest.CoffeeShopHandler;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import com.trafilea.coffeeshop.cart.domain.presentation.CreateOrderResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class OrderCreationHandler extends CoffeeShopHandler {

    private final CartApi cartApi;

    public OrderCreationHandler(CartApi cartApi) {
        this.cartApi = cartApi;
    }

    public Mono<ServerResponse> createOrder(String cartId) {
        return cartApi.createOrder(cartId)
                .flatMap(createOrderResponse -> generateCreatedResponse(cartId, createOrderResponse))
                .onErrorResume(this::badRequestFromCartDomainException);
    }

    private Mono<ServerResponse> generateCreatedResponse(String cartId, CreateOrderResponse createOrderResponse) {
        final var totals = new OrderTotalsJsonResponse(createOrderResponse.products(), createOrderResponse.discounts(), createOrderResponse.shipping(), createOrderResponse.order());
        return created(new CreateOrderJsonResponse(cartId, totals));
    }
}
