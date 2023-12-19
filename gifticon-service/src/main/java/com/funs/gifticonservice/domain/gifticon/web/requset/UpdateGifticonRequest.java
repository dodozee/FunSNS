package com.funs.gifticonservice.domain.gifticon.web.requset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateGifticonRequest {
    private String categoryName; // 수정할 카테고리 이름
    private String gifticonName; // 수정할 기프티콘 이름
    private String description; // 수정할 기프티콘 설명
    private Long price; // 수정할 기프티콘 가격
    private Long amount; // 수정할 기프티콘 수량

}
