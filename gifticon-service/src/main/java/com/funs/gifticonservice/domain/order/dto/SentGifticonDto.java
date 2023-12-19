package com.funs.gifticonservice.domain.order.dto;

import com.funs.gifticonservice.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SentGifticonDto {

    private Long gifticonId;
    private String gifticonName;
    private String fromUserNickname;
    private String toUserNickname;
    private String serialNumber;
    private String letter;
    private boolean isUsed;

    public static SentGifticonDto of(Order order) {
        return SentGifticonDto.builder()
                .gifticonId(order.getGifticonId())
                .gifticonName(order.getGifticonName())
                .fromUserNickname(order.getFromUserNickname())
                .toUserNickname(order.getToUserNickname())
                .serialNumber(order.getSerialNumber())
                .letter(order.getLetter())
                .isUsed(order.isUsed())
                .build();
    }
}
