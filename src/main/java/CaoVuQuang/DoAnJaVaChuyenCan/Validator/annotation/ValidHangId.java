package CaoVuQuang.DoAnJaVaChuyenCan.Validator.annotation;


import CaoVuQuang.DoAnJaVaChuyenCan.Validator.ValidHangIdValidator;
import jakarta.persistence.Table;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidHangIdValidator.class)
@Documented
public @interface ValidHangId {
    String message() default "Invalid Hang ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
