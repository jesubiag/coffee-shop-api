package com.trafilea.coffeeshop.cart.domain.model;

import lombok.With;

@With
public record CartProduct(Product product, int amount) {

    public String productId() {
        return product.id();
    }

}
