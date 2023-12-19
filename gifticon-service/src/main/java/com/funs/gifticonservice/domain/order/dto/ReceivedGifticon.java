package com.funs.gifticonservice.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceivedGifticon {
    private Long gifticonId; // 기프티콘 id
    private String gifticonName; // 기프티콘 이름
    private String fromUserNickname; // 보내는 사람 닉네임
    private String toUserNickname; // 받는 사람 닉네임
    private String serialNumber; // 기프티콘 일련번호(무작위 16자리)
    private String letter; //간단한 편지
    private boolean isUsed; //사용했는지 안했는지 여부 - 일단 넣어둠 지금 당장은 쓸순없음

    public static ReceivedGifticon of(ReceivedGifticonDto order) {
        return ReceivedGifticon.builder()
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
