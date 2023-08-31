package com.trafilea.coffeeshopapp;

import com.trafilea.coffeeshopdomain.usecases.CreateCart;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockedTestContext {

    @Bean
    public CreateCart createCart() {
        return Mockito.mock(CreateCart.class);
    }

}
