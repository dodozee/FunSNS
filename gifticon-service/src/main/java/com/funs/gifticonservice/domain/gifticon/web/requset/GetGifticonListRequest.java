package com.funs.gifticonservice.domain.gifticon.web.requset;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetGifticonListRequest {
    private String categoryName; // 조회할 카테고리 이름
}
