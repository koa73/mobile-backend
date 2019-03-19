package ru.mobile.lib.rest.validation.constraint;

import ru.mobile.lib.rest.validation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Олег on 15.10.2016.
 *
 * @NotNull should be used additionally if necessary !!!
 */
public class PhoneConstraint implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null ) {
            return true;
        }

/*
        +7 9## ###-##-## - Россия
        +375 ## ###-##-## - Беларусь
        +380 ## ###-##-## - Украина
        +7 7## ###-##-## - Казахстан
        +370 6## ##### - Литва
*/
        return value.matches("^(7[97]\\d{9})|(375\\d{9})|(380\\d{9})|(3706\\d{7})$");
    }
}
