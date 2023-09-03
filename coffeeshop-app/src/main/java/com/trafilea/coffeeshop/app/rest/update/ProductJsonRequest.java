package com.trafilea.coffeeshop.app.rest.update;

public record ProductJsonRequest(String id, String name, String category, Double price, Integer amount) {
}
