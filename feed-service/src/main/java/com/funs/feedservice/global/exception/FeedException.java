package com.funs.feedservice.global.exception;

import com.funs.feedservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FeedException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected FeedException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
