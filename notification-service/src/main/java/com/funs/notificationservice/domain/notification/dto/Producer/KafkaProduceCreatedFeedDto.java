package com.funs.notificationservice.domain.notification.dto.Producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceCreatedFeedDto {
    private Long userId;
    private String title;

    public static KafkaProduceCreatedFeedDto of(Long userId, String title) {
        return KafkaProduceCreatedFeedDto.builder()
                .userId(userId)
                .title(title)
                .build();
    }
}
