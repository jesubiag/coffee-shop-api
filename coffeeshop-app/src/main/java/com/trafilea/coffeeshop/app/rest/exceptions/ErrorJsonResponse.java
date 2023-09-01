package com.trafilea.coffeeshop.app.rest.exceptions;

import java.util.List;

public record ErrorJsonResponse(String message, List<ErrorJson> errors) {
}
