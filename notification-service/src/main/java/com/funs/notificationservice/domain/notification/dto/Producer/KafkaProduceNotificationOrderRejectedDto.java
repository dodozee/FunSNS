package com.funs.notificationservice.domain.notification.dto.Producer;

import com.funs.notificationservice.domain.notification.messagequeue.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceNotificationOrderRejectedDto {
    private Long id;
    private Long gifticonId;
    private Long hadPoint;
    private String gifticonName;
    private Long fromUserId;
    private String fromUserNickname;
    private Long toUserId;
    private String toUserNickname;
    private Long price;
    private Long amount;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;


}
