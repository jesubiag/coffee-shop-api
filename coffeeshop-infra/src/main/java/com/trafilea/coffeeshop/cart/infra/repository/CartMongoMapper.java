package com.trafilea.coffeeshop.cart.infra.repository;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.infra.mongo.CartMongo;
import com.trafilea.coffeeshop.cart.infra.mongo.ProductMongo;

import java.util.List;

public final class CartMongoMapper {

    public static CartMongo mapFromDomain(Cart domainCart) {
        return new CartMongo(domainCart.cartId(),
                domainCart.ownerId(),
                mapProducts(domainCart));
    }

    public static Cart mapToDomain(CartMongo cartMongo) {
        final var products = cartMongo.getProducts()
                .stream()
                .map(ProductMongoMapper::mapToDomain)
                .toList();
        return new Cart(cartMongo.getId(), cartMongo.getOwnerId(), products);
    }

    private static List<ProductMongo> mapProducts(Cart domainCart) {
        return domainCart.products()
                .stream()
                .map(ProductMongoMapper::mapFromDomain)
                .toList();
    }

}
