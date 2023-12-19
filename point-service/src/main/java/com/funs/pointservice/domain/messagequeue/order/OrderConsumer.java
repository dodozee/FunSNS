package com.funs.pointservice.domain.messagequeue.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funs.pointservice.domain.dto.producer.KafkaProduceOrderPlacedDto;
import com.funs.pointservice.domain.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class OrderConsumer {

    private final ObjectMapper objectMapper;
    private final PointService pointService;


    @Transactional
    @KafkaListener(topics = "orderPlaced")
    public void initUserPoint(String kafkaMessage) throws Exception {
        log.debug("## MatchingNotificationConsumer.orderPlaced");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto = objectMapper.readValue(kafkaMessage, KafkaProduceOrderPlacedDto.class);

        System.out.println("=======구버전 orderPlaced===========");
//        pointService.orderPlaced(kafkaProduceOrderPlacedDto);
    }

    //TODO order-service에서 placed를 받으면 이벤트를 발생시켜 point-service에서 point를 사용하고 성공적으로 사용이된다면
    //TODO 포인트를 감소 시킨 뒤 order-service에게 orderAccepted를 보내준다.
    //TODO 만약, 포인트가 부족하다면 order-service에게 orderRejected를 보내준다. (이때, order-service에서는 order를 취소시킨다.)

    @KafkaListener(topics = "gifticondb.gifticondb.gifticon")
    public void listenGifticon(ConsumerRecord<String, String> record) {
        // gifticon 테이블의 변경 사항을 처리하는 로직
        System.out.println("========gifticon 테이블의 변경 사항을 처리하는 로직 시작========");
//        System.out.println("record : "+ record.value());
//
//        System.out.println("record : "+ record.value().toString());
        System.out.println("========gifticon 테이블의 변경 사항을 처리하는 로직 끝========");


    }

    @Transactional
    @KafkaListener(topics = "gifticondb.gifticondb.orders")
    public void listenOrders(String message) {

        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            JsonNode afterNode = jsonNode.get("payload").get("after");
            Long id = afterNode.get("order_id").asLong();
            Long gifticonId = afterNode.get("gifticon_id").asLong();
            String gifticonName = afterNode.get("gifticon_name").asText();
            Long fromUserId = afterNode.get("from_user_id").asLong();
            String fromUserNickname = afterNode.get("from_user_nickname").asText();
            Long toUserId = afterNode.get("to_user_id").asLong();
            String toUserNickname = afterNode.get("to_user_nickname").asText();
            Long price = afterNode.get("price").asLong();
            Long amount = afterNode.get("amount").asLong();
            LocalDateTime orderTime = LocalDateTime.now();
            OrderStatus orderStatus = OrderStatus.valueOf(afterNode.get("order_status").asText());
            KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto = new KafkaProduceOrderPlacedDto(id, gifticonId, gifticonName, fromUserId, fromUserNickname, toUserId, toUserNickname, price, amount, orderTime, orderStatus);
            if(orderStatus == OrderStatus.PLACED) {
                pointService.orderPlaced(kafkaProduceOrderPlacedDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}