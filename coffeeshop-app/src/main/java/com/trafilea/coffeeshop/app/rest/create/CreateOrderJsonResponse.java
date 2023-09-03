package com.trafilea.coffeeshop.app.rest.create;

public record CreateOrderJsonResponse(String cartId, OrderTotalsJsonResponse totals) {
}
