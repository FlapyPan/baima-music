package com.baima.music.utils;

import com.baima.music.enums.FileType;
import com.baima.music.enums.ResponseType;
import com.baima.music.exception.BizException;
import org.springframework.web.multipart.MultipartFile;

public abstract class FileTypeUtil {
    public static boolean fileNotEmpty(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

    public static void checkFileType(String filename, FileType target) {
        // TODO 返回确切的错误信息
        if (filename == null) throw new BizException(ResponseType.BAD_REQUEST);
        var ext = filename.substring(filename.lastIndexOf("."));
        if (FileTypeUtil.getFileTypeFromExt(ext) != target) {
            throw new BizException(ResponseType.BAD_REQUEST);
        }
    }

    public static FileType getFileTypeFromExt(String ext) {
        if (isLyrics(ext)) {
            return FileType.LYRICS;
        }
        if (isImage(ext)) {
            return FileType.IMAGE;
        }
        if (isAudio(ext)) {
            return FileType.AUDIO;
        }
        if (isVideo(ext)) {
            return FileType.VIDEO;
        }
        return FileType.OTHER;
    }

    private static final String[] lyricsExt = {".lrc", ".txt"};

    private static Boolean isLyrics(String ext) {
        for (String perExt : lyricsExt) {
            if (perExt.equals(ext))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static final String[] videoExt = {
            ".vob", ".mp4", ".avi", ".flv",
            ".f4v", ".wmv", ".mov", ".rmvb",
            ".mkv", ".mpg", ".m4v", ".webm", ".rm",
            ".mpeg", ".asf", ".ts", ".mts"};

    private static Boolean isVideo(String ext) {
        for (String perExt : videoExt) {
            if (perExt.equals(ext))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static final String[] audioExt = {".mp3", ".aac", ".m4a", ".flac", ".ogg", ".wav"};

    private static Boolean isAudio(String ext) {
        for (String perExt : audioExt) {
            if (perExt.equals(ext))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static final String[] imageExt = {".png", ".jpg", ".jpeg", ".gif", ".webp"};

    private static Boolean isImage(String ext) {
        for (String perExt : imageExt) {
            if (perExt.equals(ext))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
