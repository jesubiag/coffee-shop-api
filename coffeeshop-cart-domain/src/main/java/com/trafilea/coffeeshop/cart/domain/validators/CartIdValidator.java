package com.trafilea.coffeeshop.cart.domain.validators;

import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class CartIdValidator {

    public static List<ValidationError> validate(String id) {
        if (!StringUtils.hasText(id)) {
            return List.of(ValidationError.EMPTY_FIELD);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}

