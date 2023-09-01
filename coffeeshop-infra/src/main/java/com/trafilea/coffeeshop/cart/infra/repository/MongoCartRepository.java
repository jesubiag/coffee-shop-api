package com.trafilea.coffeeshop.cart.infra.repository;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.infra.mongo.CartMongo;
import com.trafilea.coffeeshop.cart.infra.mongo.SpringMongoCartRepository;
import reactor.core.publisher.Mono;

public class MongoCartRepository implements CartRepository {

    private final SpringMongoCartRepository springMongoCartRepository;

    public MongoCartRepository(SpringMongoCartRepository springMongoCartRepository) {
        this.springMongoCartRepository = springMongoCartRepository;
    }

    @Override
    public Mono<String> create(Cart cart) {
        final var cartMongo = new CartMongo();
        cartMongo.ownerId = cart.userId();
        final var savedCart = springMongoCartRepository.save(cartMongo);
        return savedCart.map(c -> c.id);
    }

}
