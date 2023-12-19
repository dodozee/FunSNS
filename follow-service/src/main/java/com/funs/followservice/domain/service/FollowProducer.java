package com.funs.followservice.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.funs.followservice.domain.dto.producer.KafkaProduceFollowDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void follow(Long fromUserId, String fromUserNickname, Long toUserId,String toUserNickname) throws Exception{
        objectMapper.registerModule(new JavaTimeModule());
        KafkaProduceFollowDto kafkaProduceFollowDto = KafkaProduceFollowDto.of(fromUserId, fromUserNickname, toUserId, toUserNickname);
        String json = objectMapper.writeValueAsString(kafkaProduceFollowDto);
        kafkaTemplate.send("notifyByFollow", json);
        log.info("[FollowProducer] follow = {}", json);
    }
}
