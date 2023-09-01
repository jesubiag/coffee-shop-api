package com.trafilea.coffeeshop.cart.domain.model;

public record Product(String id, String name, Category category, Double price) {

    public enum Category {
        Coffee,
        Equipment,
        Accessories
    }

}
