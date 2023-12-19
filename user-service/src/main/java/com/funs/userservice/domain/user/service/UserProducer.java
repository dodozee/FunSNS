package com.funs.userservice.domain.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funs.userservice.domain.user.dto.producer.KafkaProduceInitUserDto;
import com.funs.userservice.domain.user.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.funs.userservice.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    // 회원정보 추가로 입력하면 발생
    public void createUser(User user) throws Exception{
        KafkaProduceInitUserDto kafkaSendUserDto = KafkaProduceInitUserDto.of(user);
        String json = objectMapper.writeValueAsString(kafkaSendUserDto);
        kafkaTemplate.send("initUserForPoint", json);
        log.info("[UserProducer] userCreated = {}", json);
    }


    public void updateUser(User user) throws Exception{
        KafkaProduceUpdateUserProfileDto kafkaSendUserDto = KafkaProduceUpdateUserProfileDto.of(user);
        String json = objectMapper.writeValueAsString(kafkaSendUserDto);
        kafkaTemplate.send("updatedUserForPoint", json);
        kafkaTemplate.send("updatedUserForGifticon", json);

        log.info("[UserProducer] userUpdated = {}", json);
    }
}
