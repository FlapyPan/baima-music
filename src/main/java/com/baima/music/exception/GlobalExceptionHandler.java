package com.baima.music.exception;

import com.baima.music.enums.ResponseType;
import com.baima.music.utils.GlobalResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<Object> bizExceptionHandler(BizException e) {
        var resp = GlobalResponse.of(e.getResponseType(), null);
        log.error(e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<Object> exceptionHandler(Exception e) {
        var resp = new GlobalResponse<>(ResponseType.INNER_ERROR.getCode(), ResponseType.INNER_ERROR.getMessage() + " " + e.getMessage(), null);
        log.error(e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = MessagingException.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<Object> messagingExceptionHandler(MessagingException e) {
        var resp = new GlobalResponse<>(ResponseType.INNER_ERROR.getCode(), ResponseType.INNER_ERROR.getMessage() + " " + e.getMessage(), null);
        log.error(e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<Object> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        var resp = GlobalResponse.of(ResponseType.USER_NOT_FOUND, null);
        log.error(e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<Object> badCredentialsHandler(BadCredentialsException e) {
        var resp = GlobalResponse.of(ResponseType.USER_PASSWORD_NOT_MATCH, null);
        log.error(e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = DisabledException.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<Object> disabledExceptionHandler(DisabledException e) {
        var resp = GlobalResponse.of(ResponseType.USER_NOT_ENABLED, null);
        log.error(e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = LockedException.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<Object> lockedExceptionHandler(LockedException e) {
        var resp = GlobalResponse.of(ResponseType.USER_LOCKED, null);
        log.error(e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<Object> accessDeniedHandler(AccessDeniedException e) {
        var resp = GlobalResponse.of(ResponseType.FORBIDDEN, null);
        log.error(e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<Object> validationExceptionHandler(ValidationException e) {
        var resp = new GlobalResponse<>(ResponseType.BAD_REQUEST.getCode(), ResponseType.BAD_REQUEST.getMessage() + " " + e.getMessage(), null);
        log.error(e.getMessage());
        return resp;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponse<String> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        var str = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        log.error(str);
        return GlobalResponse.of(ResponseType.BAD_REQUEST, str);
    }

}
