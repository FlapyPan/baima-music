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
@Constraint(validatedBy = FileSizeValidator.class)
@Documented
public @interface FileSize {
    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

    long maxSizeInMB() default 256;

    String message() default " max file size exceed";
}