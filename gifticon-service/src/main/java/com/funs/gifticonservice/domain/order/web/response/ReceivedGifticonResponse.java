package com.funs.gifticonservice.domain.order.web.response;

import com.funs.gifticonservice.domain.order.dto.ReceivedGifticonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceivedGifticonResponse {

    private Long gifticonId;
    private String gifticonName;
    private String fromUserNickname;
    private String toUserNickname;
    private String serialNumber;
    private String letter;
    private boolean isUsed;

    public ReceivedGifticonResponse(ReceivedGifticonDto receivedGifticonDto){
        this.gifticonId = receivedGifticonDto.getGifticonId();
        this.gifticonName = receivedGifticonDto.getGifticonName();
        this.fromUserNickname = receivedGifticonDto.getFromUserNickname();
        this.toUserNickname = receivedGifticonDto.getToUserNickname();
        this.serialNumber = receivedGifticonDto.getSerialNumber();
        this.letter = receivedGifticonDto.getLetter();
        this.isUsed = receivedGifticonDto.isUsed();
    }


}
