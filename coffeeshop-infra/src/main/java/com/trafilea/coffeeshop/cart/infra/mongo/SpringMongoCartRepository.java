package com.trafilea.coffeeshop.cart.infra.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringMongoCartRepository extends ReactiveMongoRepository<CartMongo, String> {
}
