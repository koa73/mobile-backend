package ru.mobile.lib.rest.validation;



import ru.mobile.lib.rest.validation.constraint.IntIDConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;
import java.lang.annotation.*;


@Documented
@Digits(integer=6, fraction=2)
@Constraint(validatedBy = IntIDConstraint.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)

public @interface IntID {

    String message() default "Value must be between {min}<=>{max}";

    int min();
    int max();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface  List {
        IntID[] value();
    }
}