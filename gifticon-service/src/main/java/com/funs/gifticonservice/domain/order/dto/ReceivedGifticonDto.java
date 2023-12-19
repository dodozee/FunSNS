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
public class ReceivedGifticonDto {

    private Long gifticonId;
    private String gifticonName;
    private String fromUserNickname;
    private String toUserNickname;
    private String serialNumber;
    private String letter;
    private boolean isUsed;

    public static ReceivedGifticonDto of(Order order) {
        return ReceivedGifticonDto.builder()
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
