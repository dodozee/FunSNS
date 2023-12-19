package com.funs.notificationservice.domain.notification.dto.Producer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceReportDto {
    private Long reporterUserId;
    private String reporterUserNickname;
    private Long reportedUserId;
    private String reportedUserNickname;
    private String reason;


}
