package com.funs.followservice.domain.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceFollowDto {
    private Long fromUserId;
    private String fromUserNickname;
    private Long toUserId;
    private String toUserNickname;

    public static KafkaProduceFollowDto of(Long fromUserId,String fromUserNickname, Long toUserId, String toUserNickname){
        return KafkaProduceFollowDto.builder()
                .fromUserId(fromUserId)
                .fromUserNickname(fromUserNickname)
                .toUserId(toUserId)
                .toUserNickname(toUserNickname)
                .build();
    }
}
