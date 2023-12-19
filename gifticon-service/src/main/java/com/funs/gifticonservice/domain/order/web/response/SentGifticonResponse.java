package com.funs.gifticonservice.domain.order.web.response;

import com.funs.gifticonservice.domain.order.dto.SentGifticonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SentGifticonResponse {

    private Long gifticonId;
    private String gifticonName;
    private String fromUserNickname;
    private String toUserNickname;
    private String serialNumber;
    private String letter;
    private boolean isUsed;

    public SentGifticonResponse(SentGifticonDto sentGifticonDto){
        this.gifticonId = sentGifticonDto.getGifticonId();
        this.gifticonName = sentGifticonDto.getGifticonName();
        this.fromUserNickname = sentGifticonDto.getFromUserNickname();
        this.toUserNickname = sentGifticonDto.getToUserNickname();
        this.serialNumber = sentGifticonDto.getSerialNumber();
        this.letter = sentGifticonDto.getLetter();
        this.isUsed = sentGifticonDto.isUsed();
    }


}
