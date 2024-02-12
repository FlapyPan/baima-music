package com.baima.music.utils;

import com.baima.music.enums.ResponseType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(name = "GlobalResponse", description = "全局返回对象")
public record GlobalResponse<T>(
        @Schema(name = "code", description = "状态码")
        Integer code,
        @Schema(name = "msg", description = "消息")
        String msg,
        @Schema(name = "data", description = "数据")
        T data
) implements Serializable {
    public static <T> GlobalResponse<T> of(ResponseType type, T data) {
        return new GlobalResponse<>(type.getCode(), type.getMessage(), data);
    }

    public static <T> GlobalResponse<T> ok(T data) {
        return of(ResponseType.OK, data);
    }
}
