package com.funs.gifticonservice.domain.gifticon.web.requset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterGifticonRequest {
    private String categoryName; // 등록할 카테고리 이름(정해진 카테고리를 보내면 됨, ex-음식, 운동용품 등 예시 일뿐 정해진건 아님 )
    private String gifticonName; // 등록할 기프티콘 이름(BHC 후라이드, 교촌 허니콤보, 피자헛 불고기피자 등)
    private String description; // 등록할 기프티콘 설명(간단한 설명- 바삭한 황금 올리브유로 만든 치킨~)
    private Long price; // 등록할 기프티콘 가격(원화 = 포인트로 생각하면됨)
    private Long amount; // 등록할 기프티콘 수량

}
