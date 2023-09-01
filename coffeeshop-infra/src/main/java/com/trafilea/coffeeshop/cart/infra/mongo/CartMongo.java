package com.trafilea.coffeeshop.cart.infra.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cart")
public class CartMongo {

    @Id
    public String id;

    public Long ownerId;

}
