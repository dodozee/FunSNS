package com.funs.gifticonservice.global.exception;

import com.funs.gifticonservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GifticonException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected GifticonException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
