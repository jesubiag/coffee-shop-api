package com.trafilea.coffeeshop.cart.domain.usecase;

import com.trafilea.coffeeshop.cart.domain.presentation.UpdateProductRequest;
import reactor.core.publisher.Mono;

public interface UpdateProduct {
    Mono<Void> execute(UpdateProductRequest request);
}
