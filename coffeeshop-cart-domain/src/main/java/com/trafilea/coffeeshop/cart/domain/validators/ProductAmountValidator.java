package com.trafilea.coffeeshop.cart.domain.validators;

import java.util.Collections;
import java.util.List;

public class ProductAmountValidator {

    public static List<ValidationError> validate(int amount) {
        if (amount < 0) {
            return List.of(ValidationError.INVALID_NUMBER);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}

