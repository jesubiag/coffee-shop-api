package com.trafilea.coffeeshop.cart.infra.usecase;

import com.trafilea.coffeeshop.InfraConfiguration;
import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.model.CartProduct;
import com.trafilea.coffeeshop.cart.domain.model.Product;
import com.trafilea.coffeeshop.cart.domain.model.Product.Category;
import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateOrder;
import com.trafilea.coffeeshop.cart.domain.validators.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = InfraConfiguration.class)
class CreateOrderImplTest {

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private CreateOrder createOrder;

    @Test
    public void shouldThrowExceptionWhenCartDoesNotExist() {
        // given
        final var nonExistentCartId = "non_existent_cart_id";
        when(cartRepository.read(nonExistentCartId)).thenReturn(Mono.just(Optional.empty()));

        // when
        final var result = createOrder.execute(nonExistentCartId);

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
    public void shouldReturnOrderGeneratedWithRulesAppliedFromCartForGivenCartId() {
        // given
        final var userId = 123L;
        final var cartId = "some_cart_id";
        final var products = List.of(
                new CartProduct(new Product("latte_id", "Latte", Category.Coffee, 5.5), 2),
                new CartProduct(new Product("coffee_maker", "Coffee Maker", Category.Accessories, 80.0), 1),
                new CartProduct(new Product("bag_id", "Bag", Category.Equipment, 22.5), 1),
                new CartProduct(new Product("mousepad_id", "Mousepad", Category.Equipment, 3.5), 3)
        );
        final var cart = new Cart(cartId, userId, products);
        when(cartRepository.read(cartId)).thenReturn(Mono.just(Optional.of(cart)));

        // when
        final var result = createOrder.execute(cartId);

        // then
        StepVerifier.create(result)
                .assertNext(order -> {
                    assertEquals(8, order.products());
                    assertEquals(0.1, order.discounts());
                    assertEquals(0, order.shipping());
                    assertEquals(new BigDecimal("111.60"), order.total());
                })
                .expectComplete()
                .verify();
    }

}