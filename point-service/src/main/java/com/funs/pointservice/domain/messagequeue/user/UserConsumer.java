package com.funs.pointservice.domain.messagequeue.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funs.pointservice.domain.dto.producer.KafkaProduceInitUserDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.funs.pointservice.domain.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class UserConsumer {

    private final ObjectMapper objectMapper;
    private final PointService pointService;


    @Transactional
    @KafkaListener(topics = "initUserForPoint")
    public void initUserPoint(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.initUserPoint");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceInitUserDto kafkaProduceInitUserDto = objectMapper.readValue(kafkaMessage, KafkaProduceInitUserDto.class);

        pointService.initUserPoint(kafkaProduceInitUserDto);
    }

    @Transactional
    @KafkaListener(topics = "updatedUserForPoint")
    public void updateUserPoint(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.updateUserPoint");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto = objectMapper.readValue(kafkaMessage, KafkaProduceUpdateUserProfileDto.class);

        pointService.updateUserPoint(kafkaProduceUpdateUserProfileDto);
    }

}
