package com.funs.notificationservice.domain.notification.dto;

import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class NotificationDto {
    private Long id;
    private String title;
    private String message;
    private boolean readYn;
    private String createdAt;

    public NotificationDto(FindNotificationDto dto){
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.message = dto.getMessage();
        this.readYn = dto.getReadYn().isYn();
        this.createdAt = dto.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
