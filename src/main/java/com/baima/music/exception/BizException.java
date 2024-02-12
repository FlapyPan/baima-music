package com.baima.music.exception;

import com.baima.music.enums.ResponseType;

public class BizException extends RuntimeException {
    private final ResponseType responseType;

    public BizException(ResponseType responseType) {
        super(responseType.getMessage());
        this.responseType = responseType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
