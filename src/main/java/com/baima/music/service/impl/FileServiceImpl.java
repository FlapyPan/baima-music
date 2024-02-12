package com.baima.music.service.impl;

import com.baima.music.entity.File;
import com.baima.music.enums.FileType;
import com.baima.music.enums.ResponseType;
import com.baima.music.exception.BizException;
import com.baima.music.repository.FileRepository;
import com.baima.music.service.FileService;
import com.baima.music.utils.Constants;
import com.baima.music.utils.FileTypeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl extends GeneralServiceImpl<File> implements FileService {
    @Getter(value = AccessLevel.PROTECTED)
    private final FileRepository repository;

    @Transactional
    @Override
    public File upload(String dir, MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new BizException(ResponseType.BAD_REQUEST);
        }
        var file = new File();
        // 获取扩展名
        var originFilename = multipartFile.getOriginalFilename();
        var ext = "";
        if (StringUtils.hasText(originFilename)) {
            ext = originFilename.substring(originFilename.lastIndexOf("."));
            file.setType(FileTypeUtil.getFileTypeFromExt(ext));
        } else {
            file.setType(FileType.OTHER);
        }
        // 新文件名
        var filename = UUID.randomUUID().toString().replace("-", "") + ext;
        // 上传到 ./upload/xxx 文件夹
        var filePath = Path.of(Constants.UPLOAD_PATH, dir, filename);
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }
        try (var is = multipartFile.getInputStream();
             var os = new FileOutputStream(filePath.toString())) {
            is.transferTo(os);
        }
        file.setSize(multipartFile.getSize());
        file.setUrl("/static/" + dir + "/" + filename);
        file.setName(filename);
        return repository.save(file);
    }


    @Override
    public ResponseType getNotFoundResponseType() {
        return ResponseType.FILE_NOT_FOUND;
    }

}
