package com.funs.userservice.domain.jwt.exception;

import com.funs.userservice.global.exception.UserException;
import org.springframework.http.HttpStatus;

public class AccessTokenNotValidException extends UserException {
    public AccessTokenNotValidException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
