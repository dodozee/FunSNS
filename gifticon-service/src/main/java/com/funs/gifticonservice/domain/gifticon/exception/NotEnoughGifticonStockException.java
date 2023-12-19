package com.funs.gifticonservice.domain.gifticon.exception;

import com.funs.gifticonservice.global.exception.GifticonException;
import org.springframework.http.HttpStatus;

public class NotEnoughGifticonStockException extends GifticonException {


    public NotEnoughGifticonStockException( String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}
