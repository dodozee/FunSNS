package com.funs.feedservice.domain.feed.exception;

import com.funs.feedservice.global.exception.FeedException;
import org.springframework.http.HttpStatus;

public class AlreadyLikedException extends FeedException {
    public AlreadyLikedException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
