package com.trafilea.coffeeshop.app.rest.update;

import java.util.List;

public record AddProductsJsonRequest(List<ProductJsonRequest> products) {
}
