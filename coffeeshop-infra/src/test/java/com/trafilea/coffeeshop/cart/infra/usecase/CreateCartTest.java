package com.trafilea.coffeeshop.cart.infra.usecase;

import com.trafilea.coffeeshop.InfraConfiguration;
import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = InfraConfiguration.class)
public class CreateCartTest {

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private CreateCart createCart;

    @Test
    public void shouldReturnJustCreatedCartId() {
        // given
        final var userId = 123L;
        final var newCart = Cart.empty(userId);
        final var cartId = "new_cart_id";
        Mockito.when(cartRepository.create(newCart)).thenReturn(Mono.just(cartId));

        // when
        final var createdCartId = createCart.execute(userId);

        // then
        StepVerifier.create(createdCartId)
                .assertNext(id -> Assertions.assertEquals(cartId, id))
                .expectComplete()
                .verify();
    }

}
