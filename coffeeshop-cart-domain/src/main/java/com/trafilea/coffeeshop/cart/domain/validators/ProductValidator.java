package com.trafilea.coffeeshop.cart.domain.validators;

import com.trafilea.coffeeshop.cart.domain.model.CartProduct;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductValidator {

    public static List<ValidationError> validate(List<CartProduct> products) {
        if (products == null || products.isEmpty())
            return List.of(ValidationError.EMPTY_FIELD);

        List<ValidationError> productErrors = products.stream()
                .collect(ArrayList::new, (validationErrors, cartProduct) -> {
                    final var product = cartProduct.product();

                    if (!StringUtils.hasText(product.id()))
                        validationErrors.add(ValidationError.EMPTY_FIELD);

                    if (!StringUtils.hasText(product.name()))
                        validationErrors.add(ValidationError.EMPTY_FIELD);

                    if (!StringUtils.hasText(product.category().toString()))
                        validationErrors.add(ValidationError.EMPTY_FIELD);

                    if (product.price() < 0)
                        validationErrors.add(ValidationError.INVALID_NUMBER);

                    if (cartProduct.amount() < 1)
                        validationErrors.add(ValidationError.INVALID_NUMBER);
                    }, List::addAll);
        if (!productErrors.isEmpty())
            return productErrors;

        return Collections.EMPTY_LIST;
    }

}
