package com.trafilea.coffeeshop.cart.domain.validators;

import com.trafilea.coffeeshop.cart.domain.model.Product;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductValidator {

    public static List<ValidationError> validate(List<Product> products) {
        if (products == null || products.isEmpty())
            return List.of(ValidationError.EMPTY_FIELD);

        List<ValidationError> productErrors = products.stream()
                .collect(ArrayList::new, (validationErrors, product) -> {
                    if (!StringUtils.hasText(product.name()))
                        validationErrors.add(ValidationError.EMPTY_FIELD);
                    if (!StringUtils.hasText(product.category().toString()))
                        validationErrors.add(ValidationError.EMPTY_FIELD);
                    if (product.price() < 0)
                        validationErrors.add(ValidationError.NO_VALUE);
                    }, List::addAll);
        if (!productErrors.isEmpty())
            return productErrors;

        return Collections.EMPTY_LIST;
    }

}
