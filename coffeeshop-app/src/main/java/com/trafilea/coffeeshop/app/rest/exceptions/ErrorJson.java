package com.trafilea.coffeeshop.app.rest.exceptions;

public record ErrorJson(String code, String field, String message) {
}
