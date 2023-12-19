package com.funs.userservice.domain.user.dto.producer;

import com.funs.userservice.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceInitUserDto {
    private Long userId;
    private String nickname;


    public static KafkaProduceInitUserDto of(User user) {
        return KafkaProduceInitUserDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
