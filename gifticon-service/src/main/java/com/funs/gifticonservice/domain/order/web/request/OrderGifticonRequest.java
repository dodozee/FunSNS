package com.funs.gifticonservice.domain.order.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderGifticonRequest {
    private Long gifticonId; // 기프티콘 id
    private String toUserNickname; // 기프티콘을 받을 유저의 닉네임
    private Long amount; // 기프티콘 주문 수량
    private String letter; // 기프티콘에 담길 짧은 편지
}
