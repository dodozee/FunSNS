package com.funs.pointservice.domain.service;


import com.funs.pointservice.domain.dto.PointDetailHistoryDto;
import com.funs.pointservice.domain.dto.PointDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceCreatedFeedDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceInitUserDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceOrderPlacedDto;
import com.funs.pointservice.domain.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.funs.pointservice.domain.entity.Point;
import com.funs.pointservice.domain.entity.PointTransaction;
import com.funs.pointservice.domain.exception.NotExistUserException;
import com.funs.pointservice.domain.repository.PointRepository;
import com.funs.pointservice.domain.repository.PointTransactionRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointServiceImpl implements PointService{


    private final PointRepository pointRepository;
    private final PointTransactionRepositoryCustom pointTransactionRepositoryCustom;
    private final PointProducer pointProducer;

    @Transactional
    @Override
    public void initUserPoint(KafkaProduceInitUserDto kafkaProduceInitUserDto) {
        if(pointRepository.findByUserId(kafkaProduceInitUserDto.getUserId()) != null){
            return ;
        }
        Point point = Point.init(kafkaProduceInitUserDto.getUserId(), kafkaProduceInitUserDto.getNickname(), 1000L); //회원가입시 1000 point
        pointRepository.save(point);

    }







    @Transactional
    @Override
    public void orderPlaced(KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto) throws Exception {
        System.out.println("orderPlaced 시작 ============kafkaProduceOrderPlacedDto = " + kafkaProduceOrderPlacedDto);
        Long fromUserId = kafkaProduceOrderPlacedDto.getFromUserId();
        System.out.println("orderPlaced 시작 =====fromUserId = " + fromUserId);
        String fromUserName = kafkaProduceOrderPlacedDto.getFromUserNickname();
        System.out.println("orderPlaced 시작 =====fromUserName = " + fromUserName);
        String toUserName = kafkaProduceOrderPlacedDto.getToUserNickname();
        System.out.println("orderPlaced 시작 =====toUserName = " + toUserName);
        String gifticonName = kafkaProduceOrderPlacedDto.getGifticonName();
        System.out.println("orderPlaced 시작 =====gifticonName = " + gifticonName);
        Long gifticonId = kafkaProduceOrderPlacedDto.getGifticonId();
        System.out.println("orderPlaced 시작 =====gifticonId = " + gifticonId);
        Long gifticonPrice = kafkaProduceOrderPlacedDto.getPrice()* kafkaProduceOrderPlacedDto.getAmount();
        Point point = pointRepository.findByUserId(fromUserId);
        System.out.println("orderPlaced 시작 =====point.getNickname() = " + point.getNickname());
        System.out.println("orderPlaced 시작 =====point.getBalance() = " + point.getBalance());
        System.out.println("orderPlaced 시작 =====gifticonPrice = " + gifticonPrice);
        boolean checked = checkValidationDecreasePoint(point, kafkaProduceOrderPlacedDto);
        System.out.println("orderPlaced 시작 =====checked = " + checked);
        if(checked) {
            System.out.println("orderPlaced 시작 =====if문 시작? = " + checked);
            point.decreasePoint(gifticonPrice, "기프티콘 구매 : No." + gifticonId + " / " + fromUserName + "님이 " + gifticonName + " 기프티콘 을 " + toUserName + "님에게 / point: " + gifticonPrice + " 차감");
            pointProducer.sendOrderAccepted(kafkaProduceOrderPlacedDto, point.getId());
        }else{
            pointProducer.sendOrderRejectedByNotEnoughPoint(kafkaProduceOrderPlacedDto, point.getBalance());
        }


    }

    @Override
    public PointDto getPoint(String userId) {
        Point point = pointRepository.findByUserId(Long.parseLong(userId));
        if(point != null) {
            return PointDto.builder()
                    .userId(point.getUserId())
                    .nickname(point.getNickname())
                    .balance(point.getBalance())
                    .build();
        }else{
            throw new NotExistUserException("해당 유저가 존재하지 않습니다.");
        }
    }

    //TODO : QueryDSL로 바꿔야함
    @Override
    public Page<PointDetailHistoryDto> getPointDetailHistory(String userId, Pageable pageable) {
        Page<PointTransaction> pointTransactionList = pointTransactionRepositoryCustom.getPointTransactionHistoryByUserId(Long.parseLong(userId), pageable);
        System.out.println("pointTransactionList = " + pointTransactionList);
        return PageableExecutionUtils.getPage(pointTransactionList.stream()
                .map(PointDetailHistoryDto::of)
                .collect(Collectors.toList()), pageable, pointTransactionList::getTotalElements);
    }

    @Transactional
    @Override
    public void updateUserPoint(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto) {
        Point point = pointRepository.findByUserId(kafkaProduceUpdateUserProfileDto.getUserId());
        point.updateUserNickname(kafkaProduceUpdateUserProfileDto.getNickname());
    }

    @Transactional
    @Override
    public void increasePointByCreatedFeed(KafkaProduceCreatedFeedDto kafkaProduceCreatedFeedDto) {
        Point point = pointRepository.findByUserId(kafkaProduceCreatedFeedDto.getUserId());
        String title = kafkaProduceCreatedFeedDto.getTitle();
//        PointTransaction pointTransaction = PointTransaction.of(kafkaProduceCreatedFeedDto.getUserId(), 0, "피드 작성 :" + title , 100L, point.getBalance()+100L);
        point.increasePoint(100L, "피드 작성 :" + title);
    }

    private boolean checkValidationDecreasePoint(Point point, KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto) {
        System.out.println("=========checkValidationDecreasePoint메서드 실행 ==========point.getBalance() = " + point.getBalance());
        return point.getBalance() - kafkaProduceOrderPlacedDto.getPrice() * kafkaProduceOrderPlacedDto.getAmount() >= 0;
    }

}
