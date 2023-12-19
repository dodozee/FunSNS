package com.funs.gifticonservice.domain.order.exception;

import com.funs.gifticonservice.global.exception.GifticonException;
import org.springframework.http.HttpStatus;

public class OrderException extends GifticonException {
    public OrderException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
