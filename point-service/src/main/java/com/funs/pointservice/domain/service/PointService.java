package com.funs.pointservice.domain.service;

import com.funs.pointservice.domain.dto.PointDetailHistoryDto;
import com.funs.pointservice.domain.dto.PointDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceCreatedFeedDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceInitUserDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceOrderPlacedDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceUpdateUserProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointService {
    void initUserPoint(KafkaProduceInitUserDto kafkaProduceInitUserDto);

    void orderPlaced(KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto) throws Exception;

    PointDto getPoint(String userId);

    Page<PointDetailHistoryDto> getPointDetailHistory(String userId, Pageable pageable
    );

    void updateUserPoint(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto);

    void increasePointByCreatedFeed(KafkaProduceCreatedFeedDto kafkaProduceCreatedFeedDto);
}
