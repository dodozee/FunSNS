package com.funs.notificationservice.domain.notification.messagequeue.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceNotificationOrderAcceptedDto;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceNotificationOrderRejectedDto;
import com.funs.notificationservice.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class OrderNotificationConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;


    @Transactional
    @KafkaListener(topics = "notifyByOrderAccepted")
    public void notifyByOrderAccepted(String kafkaMessage) throws JsonProcessingException {
        log.debug("## NotificationConsumer.notifyByOrderAccepted");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceNotificationOrderAcceptedDto kafkaProduceNotificationOrderAcceptedDto = objectMapper.readValue(kafkaMessage, KafkaProduceNotificationOrderAcceptedDto.class);

        notificationService.notifyByOrderAccepted(kafkaProduceNotificationOrderAcceptedDto);
    }

    @Transactional
    @KafkaListener(topics = "notifyOrderRejected")
    public void notifyOrderRejected(String kafkaMessage) throws JsonProcessingException {
        log.debug("## NotificationConsumer.notifyOrderRejected");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceNotificationOrderRejectedDto kafkaProduceNotificationOrderRejectedDto = objectMapper.readValue(kafkaMessage, KafkaProduceNotificationOrderRejectedDto.class);

        notificationService.notifyOrderRejected(kafkaProduceNotificationOrderRejectedDto);
    }



}
