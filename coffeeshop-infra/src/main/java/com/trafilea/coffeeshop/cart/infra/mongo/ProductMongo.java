package com.trafilea.coffeeshop.cart.infra.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMongo {

    public String id;

    public String name;

    public String category;

    public Double price;

}
