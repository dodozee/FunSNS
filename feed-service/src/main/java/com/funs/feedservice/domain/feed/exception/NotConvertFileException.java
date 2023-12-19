package com.funs.feedservice.domain.feed.exception;

import com.funs.feedservice.global.exception.FeedException;
import org.springframework.http.HttpStatus;

public class NotConvertFileException extends FeedException {
    public NotConvertFileException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}

