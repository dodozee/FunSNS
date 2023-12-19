package com.funs.notificationservice.domain.notification.dto;


import com.funs.notificationservice.domain.notification.entity.Notification;
import com.funs.notificationservice.global.dto.Yn;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class FindNotificationDto {
    private Long id;//알림 아이디
    private Long userId;//유저 아이디
    private String message;//알림 메세지
    private String title;//알림 제목
    private Yn readYn;//읽음 여부
    private LocalDateTime createdAt;//알림 발생시간 : 몇분전,몇시간전,몇일전 이런거 사용 가능 용도

    public FindNotificationDto(Notification entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.message = entity.getMessage();
        this.title = entity.getTitle();
        this.readYn = entity.getReadYn();
        this.createdAt = entity.getCreatedAt();
    }


}
