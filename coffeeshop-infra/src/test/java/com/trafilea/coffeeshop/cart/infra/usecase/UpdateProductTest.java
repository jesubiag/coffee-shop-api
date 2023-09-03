package com.trafilea.coffeeshop.cart.infra.usecase;

import com.trafilea.coffeeshop.InfraConfiguration;
import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.model.CartProduct;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import com.trafilea.coffeeshop.cart.domain.presentation.UpdateProductRequest;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.domain.usecase.UpdateProduct;
import com.trafilea.coffeeshop.cart.domain.validators.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.trafilea.coffeeshop.cart.domain.model.Product.Category.Accessories;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = InfraConfiguration.class)
public class UpdateProductTest {

    @Autowired
    private UpdateProduct updateProduct;

    @MockBean
    private CartRepository cartRepository;


    @Test
    public void shouldThrowExceptionWhenCartDoesNotExist() {
        // given
        final var nonExistentCartId = "non_existent_cart_id";
        final var productId = "some_product_id";
        final var request = new UpdateProductRequest(nonExistentCartId, productId, 2);
        final Mono<Optional<Cart>> noCart = Mono.just(Optional.empty());

        when(cartRepository.read(nonExistentCartId)).thenReturn(noCart);

        // when
        final var result = updateProduct.execute(request);

        // then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> {
                    final var cartDomainException = (CartDomainException) throwable;
                    final var validationError = ValidationError.INVALID_CART_ID;
                    final var cartError = cartDomainException.errors.get(0);
                    return "cartId".equals(cartError.field())
                            && Objects.equals(validationError.code, cartError.code())
                            && Objects.equals(validationError.message, cartError.description());
                })
                .verify();
    }

    @Test
    public void shouldThrowExceptionWhenProductDoesNotExist() {
        // given
        final var userId = 123L;
        final var cartId = "some_cart_id";
        final var nonExistentProductId = "non_existent_product_id";
        final var request = new UpdateProductRequest(cartId, nonExistentProductId, 2);
        final var product = new Product("product_id", "Product", Accessories, 10d);
        final var someCart = new Cart(cartId, userId, List.of(new CartProduct(product, 1)));
        final Mono<Optional<Cart>> readCart = Mono.just(Optional.of(someCart));

        when(cartRepository.read(cartId)).thenReturn(readCart);

        // when
        final var result = updateProduct.execute(request);

        // then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> {
                    final var cartDomainException = (CartDomainException) throwable;
                    final var validationError = ValidationError.INVALID_PRODUCT_ID;
                    final var cartError = cartDomainException.errors.get(0);
                    return "productId".equals(cartError.field())
                            && Objects.equals(validationError.code, cartError.code())
                            && Objects.equals(validationError.message, cartError.description());
                })
                .verify();
    }

    @Test
    public void shouldUpdateProductAmountForGivenProductWithGivenAmount() {
        // given
        final var userId = 123L;
        final var cartId = "some_cart_id";
        final var productId = "some_product_id";
        final var newAmount = 4;
        final var request = new UpdateProductRequest(cartId, productId, newAmount);
        final var product = new Product(productId, "Product", Accessories, 10d);
        final var someCart = new Cart(cartId, userId, List.of(new CartProduct(product, 1)));
        final Mono<Optional<Cart>> readCart = Mono.just(Optional.of(someCart));
        final var cartWithUpdatedProductAmount = new Cart(cartId, userId, List.of(new CartProduct(product, newAmount)));

        when(cartRepository.read(cartId)).thenReturn(readCart);
        when(cartRepository.update(cartWithUpdatedProductAmount)).thenReturn(Mono.empty());

        // when
        final var result = updateProduct.execute(request);

        // then
        StepVerifier.create(result).verifyComplete();
        verify(cartRepository).update(cartWithUpdatedProductAmount);
    }

}