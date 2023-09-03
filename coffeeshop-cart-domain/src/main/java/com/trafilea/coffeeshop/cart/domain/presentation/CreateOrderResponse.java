package com.trafilea.coffeeshop.cart.domain.presentation;

public record CreateOrderResponse(int products, double discounts, double shipping, double order) {
}
