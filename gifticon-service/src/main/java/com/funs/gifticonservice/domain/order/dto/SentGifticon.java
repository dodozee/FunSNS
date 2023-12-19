package com.funs.gifticonservice.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SentGifticon {

    private Long gifticonId;
    private String gifticonName;
    private String fromUserNickname;
    private String toUserNickname;
    private String serialNumber;
    private String letter;
    private boolean isUsed;

    public static SentGifticon of(SentGifticonDto order) {
        return SentGifticon.builder()
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
