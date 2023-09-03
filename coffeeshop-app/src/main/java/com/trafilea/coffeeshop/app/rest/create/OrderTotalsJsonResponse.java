package com.trafilea.coffeeshop.app.rest.create;

public record OrderTotalsJsonResponse(int products, double discounts, double shipping, double order) {
}
