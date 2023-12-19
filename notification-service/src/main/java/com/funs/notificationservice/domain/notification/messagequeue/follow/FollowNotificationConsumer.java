package com.funs.notificationservice.domain.notification.messagequeue.follow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceFollowDto;
import com.funs.notificationservice.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class FollowNotificationConsumer {
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    @Transactional
    @KafkaListener(topics = "notifyByFollow")
    public void notifyByFollow(String kafkaMessage) throws JsonProcessingException {
        log.debug("## NotificationConsumer.notifyByFollow");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceFollowDto kafkaProduceFollowDto = objectMapper.readValue(kafkaMessage, KafkaProduceFollowDto.class);

        notificationService.notifyByFollow(kafkaProduceFollowDto);
    }
}
