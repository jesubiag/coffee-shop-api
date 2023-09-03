package com.trafilea.coffeeshop.app;

import com.trafilea.coffeeshop.InfraConfiguration;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import com.trafilea.coffeeshop.cart.domain.usecase.AddProducts;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateCart;
import com.trafilea.coffeeshop.cart.domain.usecase.UpdateProduct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(InfraConfiguration.class)
public class CoffeeshopConfiguration {

    @Bean
    public CartApi cartApi(CreateCart createCart, AddProducts addProducts, UpdateProduct updateProduct) {
        return new CartApi(createCart, addProducts, updateProduct);
    }

}
