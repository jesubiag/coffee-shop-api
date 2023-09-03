package com.trafilea.coffeeshop.cart.infra.repository;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.model.Product;
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
                .map(cartMongo -> {
                    final var products = cartMongo.getProducts()
                            .stream()
                            .map(p -> new Product(p.id, p.name, Product.Category.valueOf(p.category), p.price))
                            .toList();
                    return Optional.of(new Cart(cartMongo.getId(), cartMongo.getOwnerId(), products));
                })
                .switchIfEmpty(Mono.just(Optional.empty()));
    }

    @Override
    public Mono<Void> update(Cart cart) {
        return mapAndSave(cart).then();
    }

    private Mono<CartMongo> mapAndSave(Cart cart) {
        final var cartMongo = CartMongoMapper.map(cart);
        return springMongoCartRepository.save(cartMongo);
    }

}
