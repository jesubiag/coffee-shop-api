package com.trafilea.coffeeshopapp.rest.create;

import com.trafilea.coffeeshopapp.rest.CoffeeShopController;
import com.trafilea.coffeeshopdomain.api.CartApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users/{userId}")
public class CartCreationController extends CoffeeShopController {

    private final CartApi cartApi;

    public CartCreationController(CartApi cartApi) {
        this.cartApi = cartApi;
    }

    @PostMapping(value = "/cart", produces="application/json")
    public Mono<ResponseEntity<CartCreationJsonResponse>> createCart(@PathVariable("userId") Long userId) {
        return cartApi.createCart(userId)
                .map(CartCreationJsonResponse::new)
                .map(this::created);
    }

}
