package com.trafilea.coffeeshop.app.rest.create;

import com.trafilea.coffeeshop.app.rest.exceptions.ErrorJson;
import com.trafilea.coffeeshop.app.rest.exceptions.ErrorJsonResponse;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import com.trafilea.coffeeshop.cart.domain.presentation.CartError;
import com.trafilea.coffeeshop.cart.domain.presentation.CreateOrderResponse;
import com.trafilea.coffeeshop.cart.domain.validators.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(OrderCreationHandler.class)
public class OrderCreationHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CartApi cartApi;

    @Test
    public void shouldReturnBadRequestWhenCreatingOrderFromANonExistentCart() {
        // given
        final var userId = 123L;
        final var nonExistentCartId = "non_existent_cart_id";
        final var validationError = ValidationError.INVALID_CART_ID;
        final var nonExistentCartException = new CartDomainException(List.of(new CartError(validationError.code, "id", validationError.message)));
        when(cartApi.createOrder(nonExistentCartId)).thenReturn(Mono.error(nonExistentCartException));

        // when
        final var response = webClient.post().uri("/users/{userId}/carts/{cartId}/orders", userId, nonExistentCartId).exchange();

        // then
        final var errorJsonResponse = new ErrorJsonResponse("Bad request", List.of(new ErrorJson(validationError.code, "id", validationError.message)));
        response.expectStatus().isBadRequest()
                .expectBody(ErrorJsonResponse.class).isEqualTo(errorJsonResponse);
    }

    @Test
    public void shouldReturnCreatedWhenCreatingOrderCorrectlyFromGivenCart() {
        // given
        final var userId = 123L;
        final var cartId = "cartId";
        final var totalProducts = 3;
        final var totalDiscounts = 20.41;
        final var totalShipping = 7.5;
        final var totalOrder = 60.11;
        final var createOrderResponse = new CreateOrderResponse(totalProducts, totalDiscounts, totalShipping, totalOrder);
        when(cartApi.createOrder(cartId)).thenReturn(Mono.just(createOrderResponse));

        // when
        final var response = webClient.post().uri("/users/{userId}/carts/{cartId}/orders", userId, cartId).exchange();

        // then
        final var createOrderJsonResponse = new CreateOrderJsonResponse(cartId, new OrderTotalsJsonResponse(totalProducts, totalDiscounts, totalShipping, totalOrder));
        response.expectStatus().isCreated()
                .expectBody(CreateOrderJsonResponse.class).isEqualTo(createOrderJsonResponse);
    }

}
