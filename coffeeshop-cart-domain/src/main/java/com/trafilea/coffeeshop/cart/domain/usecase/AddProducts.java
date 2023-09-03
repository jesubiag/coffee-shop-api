package com.trafilea.coffeeshop.cart.domain.usecase;

import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import reactor.core.publisher.Mono;

public interface AddProducts {
    Mono<Void> execute(AddProductsRequest request);
}
