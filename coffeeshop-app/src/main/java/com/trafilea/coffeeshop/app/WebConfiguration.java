package com.trafilea.coffeeshop.app;

import com.trafilea.coffeeshop.app.rest.create.CartCreationHandler;
import com.trafilea.coffeeshop.app.rest.update.AddProductsJsonRequest;
import com.trafilea.coffeeshop.app.rest.update.CartUpdateHandler;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebConfiguration implements WebFluxConfigurer {

    @Bean
    public CartCreationHandler cartCreationHandler(CartApi cartApi) {
        return new CartCreationHandler(cartApi);
    }

    @Bean
    public CartUpdateHandler cartUpdateHandler(CartApi cartApi) {
        return new CartUpdateHandler(cartApi);
    }

    @Bean
    public RouterFunction<ServerResponse> createCartHandler(CartCreationHandler cartCreationHandler) {
        return route(
                POST("/users/{userId}/carts").and(accept(MediaType.APPLICATION_JSON)),
                rq -> cartCreationHandler.createCart(Long.parseLong(rq.pathVariable("userId")))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateCartHandler(CartUpdateHandler cartUpdateHandler) {
        return route(
                POST("/users/{userId}/carts/{cartId}").and(accept(MediaType.APPLICATION_JSON)),
                rq -> rq.bodyToMono(AddProductsJsonRequest.class)
                        .flatMap(addProductsJsonRequest -> cartUpdateHandler.addProducts(rq.pathVariable("cartId"), addProductsJsonRequest))
        );
    }

}
