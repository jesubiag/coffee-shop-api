package com.trafilea.coffeeshop.cart.infra.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMongo {

    private String id;

    private String name;

    private String category;

    private Double price;

    private Integer amount;

}
