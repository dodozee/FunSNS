package com.funs.pointservice.domain.dto.producer;

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


}
