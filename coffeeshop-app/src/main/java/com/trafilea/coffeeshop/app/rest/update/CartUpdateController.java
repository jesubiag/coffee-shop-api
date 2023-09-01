package com.trafilea.coffeeshop.app.rest.update;

import com.trafilea.coffeeshop.app.rest.CoffeeShopController;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users/{userId}/carts/{cartId}")
public class CartUpdateController extends CoffeeShopController {

    private final CartApi cartApi;

    public CartUpdateController(CartApi cartApi) {
        this.cartApi = cartApi;
    }

    @PostMapping(produces="application/json")
    public Mono<ResponseEntity<Void>> addProducts(@PathVariable String cartId,
                                                  @RequestBody AddProductsJsonRequest request) {
        final var products = request.products()
                .stream()
                .map(productJson -> new Product(null, productJson.name(), Product.Category.valueOf(productJson.category()), productJson.price()))
                .toList();
        final var addProductsRequest = AddProductsRequest.factory(cartId, products);
        cartApi.addProducts(addProductsRequest);
        return Mono.just(ResponseEntity.noContent().build());
    }

}
