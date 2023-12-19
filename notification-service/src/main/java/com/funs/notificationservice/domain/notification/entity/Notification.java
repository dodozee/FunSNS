package com.funs.notificationservice.domain.notification.entity;

import com.funs.notificationservice.global.dto.Yn;
import com.funs.notificationservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "team_notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "team_notification_id")
    private Long id;
    private Long userId; // userdb.users.user_id
    private String title;
    private String message;

    @Enumerated(EnumType.STRING)
    private Yn readYn;

    public static Notification of(Long userId, String message, String title) {
        Notification notification = new Notification();
        notification.userId = userId;
        notification.message = message;
        notification.title = title;
        notification.readYn = Yn.N; // default
        return notification;
    }

    public void updateReadYn(Yn readYn) {
        this.readYn = readYn;
    }



}
