package com.trafilea.coffeeshopapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class CoffeeShopController {

    protected <T> ResponseEntity<T> ok(T entity) {
        return buildResponse(entity, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> created(T entity) {
        return buildResponse(entity, HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<T> buildResponse(T entity, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(entity);
    }

}
