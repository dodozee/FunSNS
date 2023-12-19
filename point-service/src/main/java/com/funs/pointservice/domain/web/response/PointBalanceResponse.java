package com.funs.pointservice.domain.web.response;

import com.funs.pointservice.domain.dto.PointDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointBalanceResponse {
    private Long userId; // 사용자 아이디
    private String nickname; //사용자 닉네임
    private Long balance; // 포인트 잔액


    public static PointBalanceResponse of(PointDto pointDto) {
        return PointBalanceResponse.builder()
                .userId(pointDto.getUserId())
                .nickname(pointDto.getNickname())
                .balance(pointDto.getBalance())
                .build();
    }
}
