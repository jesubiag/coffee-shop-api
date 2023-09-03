package com.trafilea.coffeeshop.cart.infra.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartMongo {

    @Id
    private String id;

    private Long ownerId;

    private List<ProductMongo> products = new ArrayList<>();

}
