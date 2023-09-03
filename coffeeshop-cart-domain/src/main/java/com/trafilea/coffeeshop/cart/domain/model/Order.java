package com.trafilea.coffeeshop.cart.domain.model;

import lombok.With;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@With
public record Order(String cartId, int products, double discounts, double shipping, double productsSubtotal) {

    public Order addProduct(CartProduct cartProduct) {
        final var newProductOrder = fromProducts(cartId, List.of(cartProduct));
        return plus(newProductOrder);
    }

    public BigDecimal total() {
        return calculateOrderTotal(productsSubtotal, shipping, discounts).setScale(2, RoundingMode.HALF_UP);
    }

    public static Order fromProducts(String cartId, List<CartProduct> cartProducts) {
        final var products = cartProducts.stream().mapToInt(CartProduct::amount).sum();
        final var discounts = 0;
        final var shipping = 3 * products;
        final var productsSubtotal = cartProducts.stream()
                .mapToDouble(cartProduct -> cartProduct.amount() * cartProduct.product().price())
                .sum();

        return new Order(cartId, products, discounts, shipping, productsSubtotal);
    }

    private static BigDecimal calculateOrderTotal(double productsSubtotal, double shippingTotal, double discountsTotal) {
        return BigDecimal.valueOf((productsSubtotal + shippingTotal) * (1 - discountsTotal));
    }

    private Order plus(Order otherOrder) {
        final var addedProductsAmount = products + otherOrder.products();
        final var addedDiscounts = discounts + otherOrder.discounts;
        final var addedShipping = shipping + otherOrder.shipping();
        final var addedProductsSubtotal = productsSubtotal + otherOrder.productsSubtotal;

        return new Order(cartId,
                addedProductsAmount,
                addedDiscounts,
                addedShipping,
                addedProductsSubtotal);
    }
}
