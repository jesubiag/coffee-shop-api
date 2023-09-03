package com.trafilea.coffeeshop.app.rest;

import com.trafilea.coffeeshop.app.rest.exceptions.ErrorJson;
import com.trafilea.coffeeshop.app.rest.exceptions.ErrorJsonResponse;
import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class CoffeeShopHandler {

    protected <T> Mono<ServerResponse> ok(T entity) {
        return buildResponse(entity, HttpStatus.OK);
    }

    protected <T> Mono<ServerResponse> created(T entity) {
        return buildResponse(entity, HttpStatus.CREATED);
    }

    protected Mono<ServerResponse> badRequestFromCartDomainException(Throwable throwable) {
        if (throwable instanceof CartDomainException cartDomainException) {
            final var errorJsonResponse = new ErrorJsonResponse("Bad request",
                    cartDomainException.errors.stream()
                            .map(error -> new ErrorJson(error.code(), error.field(), error.description()))
                            .toList());
            return ServerResponse.badRequest().bodyValue(errorJsonResponse);
        }

        return ServerResponse.badRequest().bodyValue(throwable.toString());
    }

    protected <T> Mono<ServerResponse> buildResponse(T body, HttpStatus httpStatus) {
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body);
    }

}
