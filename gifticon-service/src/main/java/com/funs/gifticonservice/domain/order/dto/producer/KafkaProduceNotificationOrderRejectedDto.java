package com.funs.gifticonservice.domain.order.dto.producer;

import com.funs.gifticonservice.domain.order.entity.Order;
import com.funs.gifticonservice.domain.order.entity.OrderStatus;
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

    public static KafkaProduceNotificationOrderRejectedDto of(Order order, Long hadPoint) {
        return KafkaProduceNotificationOrderRejectedDto.builder()
                .id(order.getId())
                .gifticonId(order.getGifticonId())
                .hadPoint(hadPoint)
                .gifticonName(order.getGifticonName())
                .fromUserId(order.getFromUserId())
                .fromUserNickname(order.getFromUserNickname())
                .toUserId(order.getToUserId())
                .toUserNickname(order.getToUserNickname())
                .price(order.getPrice())
                .amount(order.getAmount())
                .orderTime(order.getOrderTime())
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
