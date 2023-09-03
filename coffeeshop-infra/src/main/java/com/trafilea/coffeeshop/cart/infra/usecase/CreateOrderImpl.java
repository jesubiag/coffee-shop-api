package com.trafilea.coffeeshop.cart.infra.usecase;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import com.trafilea.coffeeshop.cart.domain.model.CartProduct;
import com.trafilea.coffeeshop.cart.domain.model.Order;
import com.trafilea.coffeeshop.cart.domain.model.Product.Category;
import com.trafilea.coffeeshop.cart.domain.presentation.CartDomainException;
import com.trafilea.coffeeshop.cart.domain.presentation.CartError;
import com.trafilea.coffeeshop.cart.domain.repository.CartRepository;
import com.trafilea.coffeeshop.cart.domain.usecase.CreateOrder;
import com.trafilea.coffeeshop.cart.domain.validators.ValidationError;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CreateOrderImpl implements CreateOrder {

    private final CartRepository cartRepository;

    public CreateOrderImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Mono<Order> execute(String cartId) {
        return cartRepository.read(cartId)
                .flatMap(possibleCart -> applyRulesOrFail(cartId, possibleCart));
    }

    private Mono<Order> applyRulesOrFail(String cartId, Optional<Cart> possibleCart) {
        return possibleCart
                .map(cart -> applyRules(cartId, cart))
                .orElse(fail());
    }

    private Mono<Order> applyRules(String cartId, Cart cart) {
        var order = Order.fromProducts(cartId, cart.products());
        for (Rule rule : CoffeeshopRules.Rules)
            order = rule.apply(cart, order);
        return Mono.just(order);
    }

    private Mono<Order> fail() {
        final var validationError = ValidationError.INVALID_CART_ID;
        return Mono.error(new CartDomainException(List.of(new CartError(validationError.code, "id", validationError.message))));
    }

    public static class CoffeeshopRules {

        /**
         * As I don't understand completely which coffee should be given for free, I'm inventing my own criteria giving some default coffee
         */
        private static final Rule extraFreeCoffeeRule = new Rule("Extra Free Coffee",
                cartProduct -> cartProduct.products().stream().filter(product -> is(product, Category.Coffee))
                        .mapToInt(CartProduct::amount).sum() >= 2,
                order -> order.addProduct(CartProduct.freeCoffee())
        );

        private static final Rule freeShippingDueToEquipmentRule = new Rule("Free Shipping Due To Equipment",
                cartProduct -> cartProduct.products().stream().filter(product -> is(product, Category.Equipment))
                        .mapToInt(CartProduct::amount).sum() > 3,
                order -> order.withShipping(0)
        );

        private static final Rule tenPercentDiscountDueToAccessoriesRule = new Rule("10% Discount Due To Accessories",
                cart -> cart.products().stream().filter(product -> is(product, Category.Accessories))
                        .mapToDouble(p -> p.amount() * p.product().price()).sum() > 70,
                order -> order.withDiscounts(0.1)
        );

        private static boolean is(CartProduct cartProduct, Category category) {
            return cartProduct.product().category() == category;
        }

        public static final List<Rule> Rules = List.of(
                extraFreeCoffeeRule,
                freeShippingDueToEquipmentRule,
                tenPercentDiscountDueToAccessoriesRule
        );

    }

    public record Rule(String name, Predicate<Cart> predicate, OrderRule orderRule) {

        public Order apply(Cart cart, Order order) {
            if (testPredicate(cart))
                return applyRule(order);
            else
                return order;
        }

        private boolean testPredicate(Cart cartProduct) {
            return predicate.test(cartProduct);
        }

        private Order applyRule(Order order) {
            return orderRule.apply(order);
        }

    }

    @FunctionalInterface
    public interface OrderRule {
        Order apply(Order order);
    }

}
