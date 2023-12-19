package com.funs.gifticonservice.domain.gifticon.exception;

import com.funs.gifticonservice.global.exception.GifticonException;
import org.springframework.http.HttpStatus;

public class NotConvertFileException extends GifticonException {
    public NotConvertFileException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}

