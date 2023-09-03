package com.trafilea.coffeeshop.cart.domain.validators;

import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class ProductIdValidator {

    public static List<ValidationError> validate(String productId) {
        if (!StringUtils.hasText(productId)) {
            return List.of(ValidationError.EMPTY_FIELD);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}

