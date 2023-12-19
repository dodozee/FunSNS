package com.funs.feedservice.domain.feed.exception;

import com.funs.feedservice.global.exception.FeedException;
import org.springframework.http.HttpStatus;

public class NotExistFeedException extends FeedException {
    public NotExistFeedException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
