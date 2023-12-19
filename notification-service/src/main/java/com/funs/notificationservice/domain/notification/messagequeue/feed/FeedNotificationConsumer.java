package com.funs.notificationservice.domain.notification.messagequeue.feed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceCreatedFeedDto;
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
public class FeedNotificationConsumer {
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    @Transactional
    @KafkaListener(topics = "notifyIncreasePointByCreatedFeed")
    public void notifyByFollow(String kafkaMessage) throws JsonProcessingException {
        log.debug("## NotificationConsumer.notifyIncreasePointByCreatedFeed");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceCreatedFeedDto kafkaProduceCreatedFeedDto = objectMapper.readValue(kafkaMessage, KafkaProduceCreatedFeedDto.class);

        notificationService.notifyByCreatedFeedDto(kafkaProduceCreatedFeedDto);
    }
}
