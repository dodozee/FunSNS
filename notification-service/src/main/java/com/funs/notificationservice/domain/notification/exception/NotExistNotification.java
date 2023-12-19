package com.funs.notificationservice.domain.notification.exception;

import com.funs.notificationservice.global.exception.NotificationException;
import org.springframework.http.HttpStatus;

public class NotExistNotification extends NotificationException {

    public NotExistNotification(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
