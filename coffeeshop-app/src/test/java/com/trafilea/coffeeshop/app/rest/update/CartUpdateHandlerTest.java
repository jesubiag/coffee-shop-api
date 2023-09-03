package com.trafilea.coffeeshop.app.rest.update;

import com.trafilea.coffeeshop.app.rest.exceptions.ErrorJson;
import com.trafilea.coffeeshop.app.rest.exceptions.ErrorJsonResponse;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import com.trafilea.coffeeshop.cart.domain.model.CartProduct;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import com.trafilea.coffeeshop.cart.domain.presentation.CartError;
import com.trafilea.coffeeshop.cart.domain.presentation.UpdateProductRequest;
import com.trafilea.coffeeshop.cart.domain.validators.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(value = CartUpdateHandler.class)
public class CartUpdateHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CartApi cartApi;

    @Test
    public void shouldReturnBadRequestWhenAddingProductsToANonExistentCart() {
        // given
        final var userId = 123L;
        final var productId = "product_id";
        final var nonExistentCartId = "non_existent_some_cart_id";
        final var productName = "Latte";
        final var productPrice = 5.50;
        final var productCategory = Product.Category.Coffee;
        final var productJsonCategory = productCategory.toString();
        final var productAmount = 1;

        final var addProductsRequest = new AddProductsRequest(nonExistentCartId, List.of(new CartProduct(new Product(productId, productName, productCategory, productPrice), productAmount)));
        final var addProductsJsonRequest = Mono.just(new AddProductsJsonRequest(List.of(new ProductJsonRequest(productId, productName, productJsonCategory, productPrice, productAmount))));
        final var validationError = ValidationError.INVALID_CART_ID;
        final var nonExistentCartException = new CartDomainException(List.of(new CartError(validationError.code, "id", validationError.message)));

        when(cartApi.addProducts(addProductsRequest)).thenReturn(Mono.error(nonExistentCartException));

        // when
        final var response = webClient.post()
                .uri("/users/{userId}/carts/{cartId}", userId, nonExistentCartId)
                .body(addProductsJsonRequest, AddProductsJsonRequest.class)
                .exchange();

        // then
        final var errorJsonResponse = new ErrorJsonResponse("Bad request", List.of(new ErrorJson(validationError.code, "id", validationError.message)));
        response.expectStatus().isBadRequest()
                .expectBody(ErrorJsonResponse.class).isEqualTo(errorJsonResponse);
    }

    @Test
    public void shouldReturnNoContentWhenProductsAreCorrectlyAdded() {
        // given
        final var userId = 123L;
        final var cartId = "ok_cart_id";
        final var productId = "product_id";
        final var productName = "Latte";
        final var productPrice = 5.50;
        final var productCategory = Product.Category.Coffee;
        final var productJsonCategory = productCategory.toString();
        final var productAmount = 1;
        final var addProductsRequest = new AddProductsRequest(cartId, List.of(new CartProduct(new Product(productId, productName, productCategory, productPrice), productAmount)));
        final var addProductsJsonRequest = Mono.just(new AddProductsJsonRequest(List.of(new ProductJsonRequest(productId, productName, productJsonCategory, productPrice, productAmount))));

        when(cartApi.addProducts(addProductsRequest)).thenReturn(Mono.empty());

        // when
        final var response = webClient.post()
                .uri("/users/{userId}/carts/{cartId}", userId, cartId)
                .body(addProductsJsonRequest, AddProductsJsonRequest.class)
                .exchange();

        // then
        response.expectStatus().isNoContent();
        verify(cartApi).addProducts(addProductsRequest);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdatingProductAmountOnANonExistentCart() {
        // given
        final var userId = 123L;
        final var nonExistentCartId = "non_existent_cart_id";
        final var productId = "some_product_id";
        final var amount = 2;
        final var updateProductRequest = new UpdateProductRequest(nonExistentCartId, productId, amount);
        final var updateProductJsonRequest = Mono.just(new UpdateProductJsonRequest(amount));
        final var validationError = ValidationError.INVALID_CART_ID;
        final var nonExistentCartException = new CartDomainException(List.of(new CartError(validationError.code, "id", validationError.message)));

        when(cartApi.updateProduct(updateProductRequest)).thenReturn(Mono.error(nonExistentCartException));

        // when
        final var response = webClient.patch()
                .uri("/users/{userId}/carts/{cartId}/products/{productId}", userId, nonExistentCartId, productId)
                .body(updateProductJsonRequest, UpdateProductJsonRequest.class)
                .exchange();

        // then
        final var errorJsonResponse = new ErrorJsonResponse("Bad request", List.of(new ErrorJson(validationError.code, "id", validationError.message)));
        response.expectStatus().isBadRequest()
                .expectBody(ErrorJsonResponse.class).isEqualTo(errorJsonResponse);
    }

    @Test
    public void shouldReturnNoContentWhenProductAmountIsCorrectlyUpdated() {
        // given
        final var userId = 123L;
        final var cartId = "ok_cart_id";
        final var productId = "some_product_id";
        final var amount = 2;
        final var updateProductRequest = new UpdateProductRequest(cartId, productId, amount);
        final var updateProductJsonRequest = Mono.just(new UpdateProductJsonRequest(amount));

        when(cartApi.updateProduct(updateProductRequest)).thenReturn(Mono.empty());

        // when
        final var response = webClient.patch()
                .uri("/users/{userId}/carts/{cartId}/products/{productId}", userId, cartId, productId)
                .body(updateProductJsonRequest, AddProductsJsonRequest.class)
                .exchange();

        // then
        response.expectStatus().isNoContent();
        verify(cartApi).updateProduct(updateProductRequest);
    }

}
