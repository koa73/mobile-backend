package ru.mobile.lib.rest.validation;


import ru.mobile.lib.rest.validation.constraint.PhoneConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneConstraint.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)

public @interface Phone {
    String message() default "phone";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface  List {
        Phone[] value();
    }
}