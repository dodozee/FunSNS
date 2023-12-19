package com.funs.gifticonservice.domain.order.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.funs.gifticonservice.domain.order.dto.producer.KafkaProduceNotificationOrderAcceptedDto;
import com.funs.gifticonservice.domain.order.dto.producer.KafkaProduceNotificationOrderRejectedDto;
import com.funs.gifticonservice.domain.order.dto.producer.KafkaProduceOrderPlacedDto;
import com.funs.gifticonservice.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void orderPlaced(Order order) throws Exception{
        objectMapper.registerModule(new JavaTimeModule());
        KafkaProduceOrderPlacedDto kafkaProduceOrderDto = KafkaProduceOrderPlacedDto.of(order);
        String json = objectMapper.writeValueAsString(kafkaProduceOrderDto);
        kafkaTemplate.send("orderPlaced", json);
        log.info("[OrderProducer] orderPlaced = {}", json);

    }

    public void notifyByOrderAccepted(Order order) throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        KafkaProduceNotificationOrderAcceptedDto kafkaProduceNotificationOrderAcceptedDto = KafkaProduceNotificationOrderAcceptedDto.of(order);
        String json = objectMapper.writeValueAsString(kafkaProduceNotificationOrderAcceptedDto);
        kafkaTemplate.send("notifyByOrderAccepted", json);
        log.info("[OrderProducer] notifyByOrderAccepted = {}", json);
    }

    public void notifyOrderRejected(Order order, Long hadPoint)throws Exception{
        objectMapper.registerModule(new JavaTimeModule());
        KafkaProduceNotificationOrderRejectedDto kafkaProduceNotificationOrderRejectedDto = KafkaProduceNotificationOrderRejectedDto.of(order,hadPoint);
        String json = objectMapper.writeValueAsString(kafkaProduceNotificationOrderRejectedDto);
        kafkaTemplate.send("notifyOrderRejected", json);
        log.info("[OrderProducer] notifyOrderRejected = {}", json);
    }

//    public void orderAccepted(Order order) throws Exception{
//        objectMapper.registerModule(new JavaTimeModule());
//        KafkaProduceOrderDto kafkaProduceOrderDto = KafkaProduceOrderDto.of(order);
//        String json = objectMapper.writeValueAsString(kafkaProduceOrderDto);
//        kafkaTemplate.send("orderAccepted", json);
//        log.info("[OrderProducer] orderAccepted = {}", json);
//
//    }

//    public void orderRejected(Order order) throws Exception{
//        objectMapper.registerModule(new JavaTimeModule());
//        KafkaProduceOrderDto kafkaProduceOrderDto = KafkaProduceOrderDto.of(order);
//        String json = objectMapper.writeValueAsString(kafkaProduceOrderDto);
//        kafkaTemplate.send("orderRejected", json);
//        log.info("[OrderProducer] orderRejected = {}", json);
//
//    }
}
