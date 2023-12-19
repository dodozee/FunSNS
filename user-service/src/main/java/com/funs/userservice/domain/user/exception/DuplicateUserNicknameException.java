package com.funs.userservice.domain.user.exception;

import com.funs.userservice.global.exception.UserException;
import org.springframework.http.HttpStatus;

public class DuplicateUserNicknameException extends UserException {

    public DuplicateUserNicknameException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
