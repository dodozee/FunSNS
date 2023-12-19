package com.funs.userservice.domain.jwt.exception;

import com.funs.userservice.global.dto.Result;
import lombok.Getter;

@Getter
public class RefreshTokenNotExistException extends RuntimeException{
    private Result result;

    public RefreshTokenNotExistException(String message){
        this.result = Result.createErrorResult(message);
    }
}
