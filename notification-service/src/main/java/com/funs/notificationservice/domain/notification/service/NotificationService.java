package com.funs.notificationservice.domain.notification.service;


import com.funs.notificationservice.domain.notification.dto.FindNotificationDto;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceCreatedFeedDto;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceFollowDto;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceNotificationOrderAcceptedDto;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceNotificationOrderRejectedDto;
import com.funs.notificationservice.domain.notification.dto.UpdateNotificationDto;
import com.funs.notificationservice.global.dto.Yn;

import java.util.List;

public interface NotificationService {

    List<FindNotificationDto> findNotificationByUserId(Long id);

    void updateNotification(UpdateNotificationDto dto);

    Long findNotificationCounts(Long userId, Yn readYn);

    void notifyByOrderAccepted(KafkaProduceNotificationOrderAcceptedDto kafkaProduceNotificationOrderAcceptedDto);

    void notifyOrderRejected(KafkaProduceNotificationOrderRejectedDto kafkaProduceNotificationOrderRejectedDto);

    void notifyByFollow(KafkaProduceFollowDto kafkaProduceFollowDto);

    void notifyByCreatedFeedDto(KafkaProduceCreatedFeedDto kafkaProduceCreatedFeedDto);
}
