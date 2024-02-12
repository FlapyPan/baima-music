package com.baima.music.service;

import com.baima.music.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService extends GeneralService<File> {
    File upload(String dir, MultipartFile multipartFile) throws IOException;
}
