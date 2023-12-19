package com.funs.gifticonservice.domain.order.web.response;


import com.funs.gifticonservice.domain.order.dto.SentGifticon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SentGifticonHistoryResponse {

        private List<SentGifticon> orders;
        private Page page;
    public SentGifticonHistoryResponse(List<SentGifticon> orders, int startPage, int totalPage) {
        this.orders = orders;
        this.page = new Page(startPage, totalPage);


    }
    @Data @AllArgsConstructor
    static class Page {
        int startPage;
        int totalPage;
    }



//    public static ReceivedGifticonResponse of(ReceivedGifticonDto dto) {
//            return ReceivedGifticonResponse.builder()
//                    .gifticonId(dto.getGifticonId())
//                    .gifticonName(dto.getGifticonName())
//                    .fromUserNickname(dto.getFromUserNickname())
//                    .toUserNickname(dto.getToUserNickname())
//                    .serialNumber(dto.getSerialNumber())
//                    .letter(dto.getLetter())
//                    .isUsed(dto.isUsed())
//                    .build();
//        }
}
