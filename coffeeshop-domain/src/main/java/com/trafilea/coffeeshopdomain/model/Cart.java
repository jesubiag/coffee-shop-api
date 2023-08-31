package com.trafilea.coffeeshopdomain.model;

import java.util.List;

public record Cart(String cartId, Long userId, List<Product> products) {
}
