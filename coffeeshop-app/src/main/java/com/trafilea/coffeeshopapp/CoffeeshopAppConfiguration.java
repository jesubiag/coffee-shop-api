package com.trafilea.coffeeshopapp;

import com.trafilea.coffeeshopdomain.DomainConfiguration;
import com.trafilea.coffeeshopdomain.api.CartApi;
import com.trafilea.coffeeshopdomain.usecases.CreateCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DomainConfiguration.class)
public class CoffeeshopAppConfiguration {

    @Bean
    public CartApi cartApi(CreateCart createCart) {
        return new CartApi(createCart);
    }

}
