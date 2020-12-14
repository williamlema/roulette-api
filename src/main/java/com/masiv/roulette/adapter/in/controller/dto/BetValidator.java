package com.masiv.roulette.adapter.in.controller.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class BetValidator implements ConstraintValidator<ValidBet, BetRequest> {
    @Override
    public boolean isValid(BetRequest value, ConstraintValidatorContext context) {
        boolean valid;
        switch (value.getType()){
            case COLOR:
                valid = Objects.nonNull(value.getColor());
                break;
            case NUMBER:
                valid = Objects.nonNull(value.getNumber());
                break;
            default:
                valid = false;
        }
        return valid;
    }
}
