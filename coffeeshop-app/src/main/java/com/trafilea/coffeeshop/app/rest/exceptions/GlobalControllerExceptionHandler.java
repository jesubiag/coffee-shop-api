package com.trafilea.coffeeshop.app.rest.exceptions;

import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = {CartDomainException.class})
    @RequestMapping(produces = "application/json")
    public ResponseEntity<ErrorJsonResponse> cartDomainException(CartDomainException ex) {
        ErrorJsonResponse errorJsonResponse = new ErrorJsonResponse("Bad request",
                ex.errors.stream()
                        .map(error -> new ErrorJson(error.code(), error.field(), error.description()))
                        .toList()

        );
        return ResponseEntity.badRequest().body(errorJsonResponse);
    }



}
