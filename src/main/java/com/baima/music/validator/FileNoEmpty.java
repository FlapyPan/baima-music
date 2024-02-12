package com.baima.music.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 文件大小校验注解
 *
 * @author FlapyPan
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileNoEmptyValidator.class)
@Documented
public @interface FileNoEmpty {
    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

    String message() default " must not be empty";
}