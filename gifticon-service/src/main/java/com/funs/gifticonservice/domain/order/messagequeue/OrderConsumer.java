package com.funs.gifticonservice.domain.order.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funs.gifticonservice.domain.order.dto.producer.KafkaProduceOrderAcceptedDto;
import com.funs.gifticonservice.domain.order.dto.producer.KafkaProduceOrderRejectedDto;
import com.funs.gifticonservice.domain.order.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.funs.gifticonservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class OrderConsumer {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @Transactional
    @KafkaListener(topics = "orderAccepted")
    public void orderAccepted(String kafkaMessage) throws JsonProcessingException, InterruptedException {
        log.debug("## MatchingNotificationConsumer.orderAccepted");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceOrderAcceptedDto kafkaProduceOrderAcceptedDto = objectMapper.readValue(kafkaMessage, KafkaProduceOrderAcceptedDto.class);

        orderService.orderAccepted(kafkaProduceOrderAcceptedDto);
    }

    @Transactional
    @KafkaListener(topics = "orderRejectedByNotEnoughPoint")
    public void increasePointByGameWin(String kafkaMessage) throws Exception {
        log.debug("## MatchingNotificationConsumer.orderAccepted");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceOrderRejectedDto kafkaProduceOrderAcceptedDto = objectMapper.readValue(kafkaMessage, KafkaProduceOrderRejectedDto.class);

        orderService.orderRejected(kafkaProduceOrderAcceptedDto);
    }

    @Transactional
    @KafkaListener(topics ="updatedUserForGifticon")
    public void updatedUserForGifticon(String kafkaMessage) throws Exception {
        log.debug("## MatchingNotificationConsumer.updatedUserForGifticon");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto = objectMapper.readValue(kafkaMessage, KafkaProduceUpdateUserProfileDto.class);

        orderService.updatedUserForGifticon(kafkaProduceUpdateUserProfileDto);
    }
}
