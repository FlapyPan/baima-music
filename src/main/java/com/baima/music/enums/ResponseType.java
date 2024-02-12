package com.baima.music.enums;

public enum ResponseType {
    OK(200, "成功"),
    INNER_ERROR(500, "系统内部错误"),
    UNAUTHORIZED(401, "用户未登录或token失效"),
    BAD_REQUEST(400, "请求错误"),
    FORBIDDEN(403, "无权操作"),
    NOT_FOUND(404, "未找到"),
    USER_NAME_DUPLICATE(40001, "用户名重复"),
    USER_NOT_FOUND(40002, "用户不存在"),
    USER_PASSWORD_NOT_MATCH(40003, "用户名或密码错误"),
    OLD_PASSWORD_NOT_MATCH(40004, "旧密码错误"),
    ARTIST_NAME_EXISTS(40005, "歌手名重复"),
    CAPTCHA_INVALID(40010, "验证码错误"),
    USER_NOT_ENABLED(50001, "用户未启用"),
    USER_LOCKED(50002, "用户被锁定"),
    MUSIC_NOT_FOUND(40401, "歌曲不存在"),
    FILE_NOT_FOUND(40402, "文件不存在"),
    FILE_NOT_PERMISSION(40301, "当前用户无权限修改文件"),
    PLAYLIST_NOT_FOUND(40404, "歌单不存在"),
    ARTIST_NOT_FOUND(40405, "歌手不存在"),
    ALBUM_NOT_FOUND(40406, "专辑不存在");

    private final Integer code;
    private final String message;

    ResponseType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
