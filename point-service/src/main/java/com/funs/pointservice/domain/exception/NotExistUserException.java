package com.funs.pointservice.domain.exception;

import com.funs.pointservice.global.exception.PointException;
import org.springframework.http.HttpStatus;

public class NotExistUserException extends PointException {
    public NotExistUserException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
