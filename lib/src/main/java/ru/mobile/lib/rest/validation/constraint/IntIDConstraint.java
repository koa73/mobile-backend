package ru.mobile.lib.rest.validation.constraint;


import ru.mobile.lib.rest.validation.IntID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * Created by Олег on 18.04.2019.
 */
public class IntIDConstraint implements ConstraintValidator<IntID, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(IntID constraintAnnotation) {

        min = constraintAnnotation.min();
        max = constraintAnnotation.max();

    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        try{

            if (value < min || value > max){
                return false;
            }
            return true;

        } catch (Exception e){

            return false;
        }
    }
}
