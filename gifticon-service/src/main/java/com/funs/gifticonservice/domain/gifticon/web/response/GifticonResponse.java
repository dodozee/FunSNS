package com.funs.gifticonservice.domain.gifticon.web.response;

import com.funs.gifticonservice.domain.gifticon.dto.GifticonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GifticonResponse {
    private Long gifticonId; // 기프티콘 id
    private String gifticonName; // 기프티콘 이름
    private String description; // 기프티콘 설명
    private Long price; // 기프티콘 가격
    private Long amount; // 기프티콘 재고
    private String image; // 기프티콘 이미지

    public GifticonResponse(GifticonDto gifticonDto){
        this.gifticonId = gifticonDto.getGifticonId();
        this.gifticonName = gifticonDto.getGifticonName();
        this.description = gifticonDto.getDescription();
        this.price = gifticonDto.getPrice();
        this.amount = gifticonDto.getAmount();
        this.image = gifticonDto.getImage();

    }
}
