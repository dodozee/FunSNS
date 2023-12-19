package com.funs.gifticonservice.domain.order.dto;

import com.funs.gifticonservice.domain.order.web.request.OrderGifticonRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderGifticonDto {

    private Long gifticonId;
    private Long fromUserId;
    private String toUserNickname;
    private Long amount;
    private String letter;

    public static OrderGifticonDto of(OrderGifticonRequest orderGifticonRequest, Long fromUserId) {
        return OrderGifticonDto.builder()
                .gifticonId(orderGifticonRequest.getGifticonId())
                .fromUserId(fromUserId)
                .toUserNickname(orderGifticonRequest.getToUserNickname())
                .amount(orderGifticonRequest.getAmount())
                .letter(orderGifticonRequest.getLetter())
                .build();
    }
}
