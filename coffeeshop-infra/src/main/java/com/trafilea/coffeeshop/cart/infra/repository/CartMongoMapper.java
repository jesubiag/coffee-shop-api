package com.trafilea.coffeeshop.cart.infra.repository;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.infra.mongo.CartMongo;
import com.trafilea.coffeeshop.cart.infra.mongo.ProductMongo;

import java.util.List;

public final class CartMongoMapper {

    public static CartMongo map(Cart domainCart) {
        return new CartMongo(domainCart.cartId(),
                domainCart.ownerId(),
                mapProducts(domainCart));
    }

    private static List<ProductMongo> mapProducts(Cart domainCart) {
        return domainCart.products()
                .stream()
                .map(ProductMongoMapper::map)
                .toList();
    }

}
