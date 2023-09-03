package com.trafilea.coffeeshop.cart.infra.repository;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.infra.mongo.CartMongo;
import com.trafilea.coffeeshop.cart.infra.mongo.SpringMongoCartRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class MongoCartRepository implements CartRepository {

    private final SpringMongoCartRepository springMongoCartRepository;

    public MongoCartRepository(SpringMongoCartRepository springMongoCartRepository) {
        this.springMongoCartRepository = springMongoCartRepository;
    }

    @Override
    public Mono<String> create(Cart cart) {
        final var savedCart = mapAndSave(cart);
        return savedCart.map(CartMongo::getId);
    }

    @Override
    public Mono<Optional<Cart>> read(String cartId) {
        return springMongoCartRepository.findById(cartId)
                .map(MongoCartRepository::mapCart)
                .switchIfEmpty(Mono.just(Optional.empty()));
    }

    @Override
    public Mono<Void> update(Cart cart) {
        return mapAndSave(cart).then();
    }

    private Mono<CartMongo> mapAndSave(Cart cart) {
        final var cartMongo = CartMongoMapper.mapFromDomain(cart);
        return springMongoCartRepository.save(cartMongo);
    }

    private static Optional<Cart> mapCart(CartMongo cartMongo) {
        return Optional.of(CartMongoMapper.mapToDomain(cartMongo));
    }

}
