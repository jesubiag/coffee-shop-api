package com.trafilea.coffeeshop.cart.infra.repository;

import com.trafilea.coffeeshop.cart.domain.model.CartProduct;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.model.Product.Category;
import com.trafilea.coffeeshop.cart.infra.mongo.ProductMongo;

public final class ProductMongoMapper {

    public static ProductMongo mapFromDomain(CartProduct domainCartProduct) {
        final var domainProduct = domainCartProduct.product();
        return new ProductMongo(domainProduct.id(),
                domainProduct.name(),
                domainProduct.category().toString(),
                domainProduct.price(),
                domainCartProduct.amount());
    }

    public static CartProduct mapToDomain(ProductMongo productMongo) {
        return new CartProduct(
                new Product(productMongo.getId(),
                        productMongo.getName(),
                        Category.valueOf(productMongo.getCategory()),
                        productMongo.getPrice()),
                productMongo.getAmount()
        );
    }

}
