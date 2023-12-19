package com.funs.notificationservice.domain.notification.dto.Producer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceRoomFullDto {
    private Long roomLeaderId;
    private String title;
    private Long capacity;

}
