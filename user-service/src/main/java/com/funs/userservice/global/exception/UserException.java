package com.funs.userservice.global.exception;

import com.funs.userservice.global.dto.Result;
import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException{
    private HttpStatus status;
    private Result errorResult;

    protected UserException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
