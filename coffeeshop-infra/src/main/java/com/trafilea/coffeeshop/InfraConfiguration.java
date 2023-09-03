package com.trafilea.coffeeshop;

import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.domain.usecase.AddProducts;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateCart;
import com.trafilea.coffeeshop.cart.infra.mongo.SpringMongoCartRepository;
import com.trafilea.coffeeshop.cart.infra.repository.MongoCartRepository;
import com.trafilea.coffeeshop.cart.infra.usecase.AddProductsImpl;
import com.trafilea.coffeeshop.cart.infra.usecase.CreateCartImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MongoConfiguration.class)
public class InfraConfiguration {

    @Bean
    public CartRepository cartRepository(SpringMongoCartRepository springMongoCartRepository) {
        return new MongoCartRepository(springMongoCartRepository);
    }

    @Bean
    public CreateCart createCart(CartRepository cartRepository) {
        return new CreateCartImpl(cartRepository);
    }

    @Bean
    public AddProducts addProducts(CartRepository cartRepository) {
        return new AddProductsImpl(cartRepository);
    }

}
