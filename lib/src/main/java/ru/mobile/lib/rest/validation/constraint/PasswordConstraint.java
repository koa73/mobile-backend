package ru.mobile.lib.rest.validation.constraint;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import ru.mobile.lib.rest.validation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * Created by Олег on 15.10.2016.
 */
public class PasswordConstraint implements ConstraintValidator<Password, Object> {

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        final String password= (String) beanWrapper.getPropertyValue("password");
        final String newPassword = (String) beanWrapper.getPropertyValue("newPassword");

        if (password.length() == 24 && newPassword.length() == 24){

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(" new and old passwords are the same").addConstraintViolation();
            return (!password.equals(newPassword));

        } else {

            return false;
        }
    }
}
