package com.baima.music.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件不为空校验
 *
 * @author FlapyPan
 */
public class FileNoEmptyValidator implements ConstraintValidator<FileNoEmpty, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile != null && !multipartFile.isEmpty();
    }
}
