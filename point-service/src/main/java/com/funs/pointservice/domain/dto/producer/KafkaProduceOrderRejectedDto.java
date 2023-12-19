package com.funs.pointservice.domain.dto.producer;


import com.funs.pointservice.domain.messagequeue.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceOrderRejectedDto {
    private Long id;
    private Long hadPoint;
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


    public static KafkaProduceOrderRejectedDto of(KafkaProduceOrderPlacedDto dto, Long hadPoint) {
        return KafkaProduceOrderRejectedDto.builder()
                .id(dto.getId())
                .hadPoint(hadPoint)
                .gifticonId(dto.getGifticonId())
                .gifticonName(dto.getGifticonName())
                .fromUserId(dto.getFromUserId())
                .fromUserNickname(dto.getFromUserNickname())
                .toUserId(dto.getToUserId())
                .toUserNickname(dto.getToUserNickname())
                .price(dto.getPrice())
                .amount(dto.getAmount())
                .orderTime(dto.getOrderTime())
                .orderStatus(OrderStatus.REJECTED)
                .build();
    }


}
