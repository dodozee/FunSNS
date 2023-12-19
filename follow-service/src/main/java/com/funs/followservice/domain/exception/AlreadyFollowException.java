package com.funs.followservice.domain.exception;

import com.funs.followservice.global.exception.FollowException;
import org.springframework.http.HttpStatus;

public class AlreadyFollowException extends FollowException {
    public AlreadyFollowException( String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
