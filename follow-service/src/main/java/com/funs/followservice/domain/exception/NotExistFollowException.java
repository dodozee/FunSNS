package com.funs.followservice.domain.exception;

import com.funs.followservice.global.exception.FollowException;
import org.springframework.http.HttpStatus;

public class NotExistFollowException extends FollowException {
    public NotExistFollowException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
