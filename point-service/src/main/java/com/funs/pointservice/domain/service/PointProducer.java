package com.funs.pointservice.domain.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.funs.pointservice.domain.dto.producer.KafkaProduceOrderAcceptedDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceOrderPlacedDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceOrderRejectedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


        public void sendOrderAccepted(KafkaProduceOrderPlacedDto dto, Long pointId) throws Exception{
        objectMapper.registerModule(new JavaTimeModule());
        KafkaProduceOrderAcceptedDto kafkaProduceOrderAcceptedDto = KafkaProduceOrderAcceptedDto.of(dto, pointId);
        String json = objectMapper.writeValueAsString(kafkaProduceOrderAcceptedDto);
        kafkaTemplate.send("orderAccepted", json);
        log.info("[OrderProducer] orderAccepted = {}", json);

    }


    public void sendOrderRejectedByNotEnoughPoint(KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto, Long hadPoint) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        KafkaProduceOrderRejectedDto kafkaProduceOrderRejectedDto = KafkaProduceOrderRejectedDto.of(kafkaProduceOrderPlacedDto, hadPoint);
        String json = objectMapper.writeValueAsString(kafkaProduceOrderRejectedDto);
        kafkaTemplate.send("orderRejectedByNotEnoughPoint", json);
        log.info("[OrderProducer] orderRejected = {}", json);
    }
}
