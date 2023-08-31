package com.trafilea.coffeeshopdomain;

import com.trafilea.coffeeshopdomain.usecases.CreateCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    public CreateCart createCart() {
        return new CreateCart();
    }

}
