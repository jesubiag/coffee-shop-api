package com.trafilea.coffeeshop.cart.domain.model;

import lombok.With;

@With
public record CartProduct(Product product, int amount) {

    public String productId() {
        return product.id();
    }

    public static CartProduct freeCoffee() {
        return new CartProduct(new Product("free_coffee", "Free coffee", Product.Category.Coffee, 0d), 1);
    }

}
