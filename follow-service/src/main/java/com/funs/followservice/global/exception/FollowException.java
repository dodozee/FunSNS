package com.funs.followservice.global.exception;

import com.funs.followservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FollowException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected FollowException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
