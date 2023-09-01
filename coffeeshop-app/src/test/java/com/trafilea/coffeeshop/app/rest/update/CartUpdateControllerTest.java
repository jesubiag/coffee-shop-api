package com.trafilea.coffeeshop.app.rest.update;

import com.trafilea.coffeeshop.app.rest.exceptions.ErrorJson;
import com.trafilea.coffeeshop.app.rest.exceptions.ErrorJsonResponse;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import com.trafilea.coffeeshop.cart.domain.presentation.CartError;
import com.trafilea.coffeeshop.cart.domain.validators.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;

@WebFluxTest(CartUpdateController.class)
public class CartUpdateControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CartApi cartApi;

    private final static String addProductsURI = "/users/{userId}/carts/{cartId}";


    @Test
    public void shouldReturnBadRequestWhenAddingProductsToANonExistentCart() {
        // given
        final var userId = 123L;
        final var nonExistentCartId = "non_existent_some_cart_id";
        final var productName = "Latte";
        final var productPrice = 5.50;
        final var productCategory = Product.Category.Coffee;
        final var productJsonCategory = productCategory.toString();

        final var addProductsRequest = new AddProductsRequest(nonExistentCartId, List.of(new Product(null, productName, productCategory, productPrice)));
        final var addProductsJsonRequest = Mono.just(new AddProductsJsonRequest(List.of(new ProductJsonRequest(productName, productJsonCategory, productPrice))));
        final var validationError = ValidationError.INVALID_CART_NUMBER;
        final var nonExistentCartException = new CartDomainException(List.of(new CartError(validationError.code, "id", validationError.message)));

        doThrow(nonExistentCartException).when(cartApi).addProducts(addProductsRequest);

        // when
        final var response = webClient.post()
                .uri(addProductsURI, userId, nonExistentCartId)
                .body(addProductsJsonRequest, AddProductsJsonRequest.class)
                .exchange();

        // then
        final var errorJsonResponse = new ErrorJsonResponse("Bad request", List.of(new ErrorJson(validationError.code, "id", validationError.message)));
        response.expectStatus().isBadRequest()
                .expectBody(ErrorJsonResponse.class).isEqualTo(errorJsonResponse);
    }

    @Test
    public void shouldReturnOkWhenProductsAreCorrectlyAdded() {
        // given
        final var userId = 123L;
        final var cartId = "ok_cart_id";
        final var productName = "Latte";
        final var productPrice = 5.50;
        final var productCategory = Product.Category.Coffee;
        final var productJsonCategory = productCategory.toString();
        final var addProductsRequest = new AddProductsRequest(cartId, List.of(new Product(null, productName, productCategory, productPrice)));
        final var addProductsJsonRequest = Mono.just(new AddProductsJsonRequest(List.of(new ProductJsonRequest(productName, productJsonCategory, productPrice))));

        // when
        final var response = webClient.post()
                .uri(addProductsURI, userId, cartId)
                .body(addProductsJsonRequest, AddProductsJsonRequest.class)
                .exchange();

        // then
        response.expectStatus().isNoContent();
        verify(cartApi).addProducts(addProductsRequest);
    }

}
