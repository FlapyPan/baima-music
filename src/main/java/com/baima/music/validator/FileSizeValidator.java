package com.baima.music.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件大小校验
 *
 * @author FlapyPan
 */
public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {
    private static final Long MB = 1048576L;
    private long maxSizeInMB;

    @Override
    public void initialize(FileSize fileSize) {
        this.maxSizeInMB = fileSize.maxSizeInMB();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null)
            return true;
        return multipartFile.getSize() < maxSizeInMB * MB;
    }
}