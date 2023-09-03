package com.trafilea.coffeeshop.app;

import com.trafilea.coffeeshop.app.rest.create.CartCreationHandler;
import com.trafilea.coffeeshop.app.rest.update.AddProductsJsonRequest;
import com.trafilea.coffeeshop.app.rest.update.CartUpdateHandler;
import com.trafilea.coffeeshop.app.rest.update.UpdateProductJsonRequest;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
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
    public RouterFunction<ServerResponse> createCartRoute(CartCreationHandler cartCreationHandler) {
        return route(
                POST("/users/{userId}/carts").and(accept(APPLICATION_JSON)),
                rq -> cartCreationHandler.createCart(userId(rq))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addProductsRoute(CartUpdateHandler cartUpdateHandler) {
        return route(
                POST("/users/{userId}/carts/{cartId}").and(accept(APPLICATION_JSON)),
                rq -> rq.bodyToMono(AddProductsJsonRequest.class)
                        .flatMap(addProductsJsonRequest -> cartUpdateHandler.addProducts(cartId(rq), addProductsJsonRequest))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateProductRoute(CartUpdateHandler cartUpdateHandler) {
        return route(
                PATCH("/users/{userId}/carts/{cartId}/products/{productId}").and(accept(APPLICATION_JSON)),
                rq -> rq.bodyToMono(UpdateProductJsonRequest.class)
                        .flatMap(updateProductJsonRequest ->
                                cartUpdateHandler.updateProduct(cartId(rq), productId(rq), updateProductJsonRequest))
        );
    }

    private static Long userId(ServerRequest request) {
        return Long.parseLong(request.pathVariable("userId"));
    }

    private static String cartId(ServerRequest request) {
        return request.pathVariable("cartId");
    }

    private static String productId(ServerRequest request) {
        return request.pathVariable("productId");
    }

}
