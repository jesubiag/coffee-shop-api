package com.trafilea.coffeeshopapp.rest;

import com.trafilea.coffeeshopapp.rest.create.CartCreationController;
import com.trafilea.coffeeshopapp.rest.create.CartCreationJsonResponse;
import com.trafilea.coffeeshopdomain.api.CartApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.just;

@WebFluxTest(CartCreationController.class)
public class CartCreationControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CartApi cartApi;

    @Test
    public void shouldCreateAnEmptyCart() {
        // given
        final var userId = 123L;
        final var cartId = "someCartId";
        when(cartApi.createCart(userId)).thenReturn(just(cartId));

        // when
        final var response = webClient.post().uri("/users/{userId}/cart", userId).exchange();

        // then
        response.expectStatus().isCreated()
                .expectBody(CartCreationJsonResponse.class).isEqualTo(new CartCreationJsonResponse(cartId));
    }

}
