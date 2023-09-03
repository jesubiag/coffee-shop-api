package com.trafilea.coffeeshop.cart.infra.usecase;

import com.trafilea.coffeeshop.InfraConfiguration;
import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.model.CartProduct;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.presentation.AddProductsRequest;
import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.domain.usecase.AddProducts;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = InfraConfiguration.class)
public class AddProductsTest {

    @Autowired
    private AddProducts addProducts;

    @MockBean
    private CartRepository cartRepository;

    @Test
    public void shouldThrowExceptionWhenCartDoesNotExist() {
        // given
        final var nonExistentCartId = "non_existent_cart_id";
        final var request = new AddProductsRequest(nonExistentCartId, List.of());

        Mono<Optional<Cart>> noCart = Mono.just(Optional.empty());
        when(cartRepository.read(nonExistentCartId)).thenReturn(noCart);

        // when
        final var result = addProducts.execute(request);

        // then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> {
                    final var cartDomainException = (CartDomainException) throwable;
                    final var validationError = ValidationError.INVALID_CART_ID;
                    final var cartError = cartDomainException.errors.get(0);
                    return "id".equals(cartError.field())
                            && Objects.equals(validationError.code, cartError.code())
                            && Objects.equals(validationError.message, cartError.description());
                })
                .verify();
    }

    @Test
    public void shouldUpdateCartWithGivenProducts() {
        // given
        final var userId = 123L;
        final var cartId = "cart_id";
        final var newProducts = List.of(
                new CartProduct(new Product("product_a", "Latte", Product.Category.Coffee, 5.25), 1),
                new CartProduct(new Product("product_b", "Brush", Product.Category.Accessories, 3.5), 2));
        final var request = new AddProductsRequest(cartId, newProducts);

        final var cartWithoutNewProducts = new Cart(cartId, userId, List.of());
        final var cartWithNewProducts = new Cart(cartId, userId, newProducts);
        Mono<Optional<Cart>> someCart = Mono.just(Optional.of(cartWithoutNewProducts));
        when(cartRepository.read(cartId)).thenReturn(someCart);
        when(cartRepository.update(cartWithNewProducts)).thenReturn(Mono.empty());

        // when
        final var result = addProducts.execute(request);

        // then
        StepVerifier.create(result).verifyComplete();
        verify(cartRepository).update(cartWithNewProducts);
    }

}
