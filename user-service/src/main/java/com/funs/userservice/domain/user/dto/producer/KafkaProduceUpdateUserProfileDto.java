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
public class KafkaProduceUpdateUserProfileDto {
    private Long userId;
    private String nickname;
    private String address;
    private String profileImageUrl;

    public static KafkaProduceUpdateUserProfileDto of(User user) {
        return KafkaProduceUpdateUserProfileDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .address(user.getArea())
                .profileImageUrl(user.getProfileImage())
                .build();
    }
}
