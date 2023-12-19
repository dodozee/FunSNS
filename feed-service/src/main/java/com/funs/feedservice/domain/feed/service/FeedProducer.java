package com.funs.feedservice.domain.feed.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.funs.feedservice.domain.feed.dto.producer.KafkaProduceCreatedFeedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void feed(Long userId, String title) throws Exception{
        objectMapper.registerModule(new JavaTimeModule());
        KafkaProduceCreatedFeedDto kafkaProduceCreatedFeedDto = KafkaProduceCreatedFeedDto.of(userId,title);
        String json = objectMapper.writeValueAsString(kafkaProduceCreatedFeedDto);
        kafkaTemplate.send("notifyIncreasePointByCreatedFeed", json);
        kafkaTemplate.send("increasePointByCreatedFeed", json);
        log.info("[FeedProducer] feed = {}", json);
    }
}
