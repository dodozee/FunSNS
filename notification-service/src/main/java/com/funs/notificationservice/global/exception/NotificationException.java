package com.funs.notificationservice.global.exception;

import com.funs.notificationservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotificationException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected NotificationException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
