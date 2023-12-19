package com.funs.pointservice.domain.dto.producer;

import com.funs.pointservice.domain.messagequeue.order.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class KafkaProduceOrderPlacedDto {
    private Long id;
    private Long gifticonId;
    private String gifticonName;
    private Long fromUserId;
    private String fromUserNickname;
    private Long toUserId;
    private String toUserNickname;
    private Long price;
    private Long amount;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;


    public KafkaProduceOrderPlacedDto(Long id, Long gifticonId, String gifticonName, Long fromUserId, String fromUserNickname, Long toUserId, String toUserNickname, Long price, Long amount,LocalDateTime localDateTime, OrderStatus orderStatus) {
        this.id = id;
        this.gifticonId = gifticonId;
        this.gifticonName = gifticonName;
        this.fromUserId = fromUserId;
        this.fromUserNickname = fromUserNickname;
        this.toUserId = toUserId;
        this.toUserNickname = toUserNickname;
        this.price = price;
        this.amount = amount;
        this.orderTime = localDateTime;
        this.orderStatus = orderStatus;
    }
}
