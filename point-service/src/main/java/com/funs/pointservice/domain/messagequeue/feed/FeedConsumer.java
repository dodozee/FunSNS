package com.funs.pointservice.domain.messagequeue.feed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funs.pointservice.domain.dto.producer.KafkaProduceCreatedFeedDto;
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
public class FeedConsumer {

    private final ObjectMapper objectMapper;
    private final PointService pointService;


    @Transactional
    @KafkaListener(topics = "increasePointByCreatedFeed")
    public void increasePointByCreatedFeed(String kafkaMessage) throws JsonProcessingException {
        log.debug("## PointConsumer.increasePointByCreatedFeed");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceCreatedFeedDto kafkaProduceCreatedFeedDto = objectMapper.readValue(kafkaMessage, KafkaProduceCreatedFeedDto.class);

        pointService.increasePointByCreatedFeed(kafkaProduceCreatedFeedDto);
    }


}
