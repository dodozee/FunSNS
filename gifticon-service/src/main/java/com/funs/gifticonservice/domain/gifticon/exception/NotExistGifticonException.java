package com.funs.gifticonservice.domain.gifticon.exception;

import com.funs.gifticonservice.global.exception.GifticonException;
import org.springframework.http.HttpStatus;

public class NotExistGifticonException extends GifticonException {

    public NotExistGifticonException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
