package com.trafilea.coffeeshop.cart.infra.repository;

import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.infra.mongo.ProductMongo;

public final class ProductMongoMapper {

    public static ProductMongo map(Product domainProduct) {
        return new ProductMongo(domainProduct.id(),
                domainProduct.name(),
                domainProduct.category().toString(),
                domainProduct.price());
    }

}
