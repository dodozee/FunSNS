package com.funs.feedservice.domain.comment.exception;

import com.funs.feedservice.global.exception.FeedException;
import org.springframework.http.HttpStatus;

public class NotExistCommentException extends FeedException {
    public NotExistCommentException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
