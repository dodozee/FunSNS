package com.funs.notificationservice.domain.notification.web.response;

import com.funs.notificationservice.domain.notification.dto.FindNotificationDto;
import com.funs.notificationservice.domain.notification.dto.NotificationDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GetNotificationResponse {
    private List<NotificationDto> notifications;

    public GetNotificationResponse(List<FindNotificationDto> notifications){
        this.notifications = notifications
                .stream()
                .map(NotificationDto::new)
                .collect(Collectors.toList());
        ;
    }
}
