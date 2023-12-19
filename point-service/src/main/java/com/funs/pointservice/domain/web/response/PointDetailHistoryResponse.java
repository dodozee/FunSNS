package com.funs.pointservice.domain.web.response;


import com.funs.pointservice.domain.dto.PointDetailHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PointDetailHistoryResponse {

        private List<PointDetailHistory> points; // 포인트 내역들
        private Page page; // 페이지 정보

    @Data @AllArgsConstructor
    static class Page {
        int startPage; // 시작 페이지 번호
        int totalPage; // 전체 페이지 번호
    }
    public PointDetailHistoryResponse(List<PointDetailHistory> points, int startPage, int totalPage) {
        this.points = points;
        this.page = new Page(startPage, totalPage);


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
