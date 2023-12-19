package com.funs.gifticonservice.domain.order.service;

import com.funs.gifticonservice.domain.gifticon.entity.Gifticon;
import com.funs.gifticonservice.domain.gifticon.exception.NotExistGifticonException;
import com.funs.gifticonservice.domain.gifticon.repository.GifticonRepository;
import com.funs.gifticonservice.domain.order.dto.OrderGifticonDto;
import com.funs.gifticonservice.domain.order.dto.ReceivedGifticonDto;
import com.funs.gifticonservice.domain.order.dto.SentGifticonDto;
import com.funs.gifticonservice.domain.order.dto.producer.KafkaProduceOrderAcceptedDto;
import com.funs.gifticonservice.domain.order.dto.producer.KafkaProduceOrderRejectedDto;
import com.funs.gifticonservice.domain.order.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.funs.gifticonservice.domain.order.entity.Order;
import com.funs.gifticonservice.domain.order.repository.OrderRepository;
import com.funs.gifticonservice.domain.order.repository.OrderRepositoryCustom;
import com.funs.gifticonservice.global.client.user.UserClient;
import com.funs.gifticonservice.global.util.SerialNumber;
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
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final GifticonRepository gifticonRepository;
    private final OrderRepositoryCustom orderRepositoryCustom;
    private final UserClient userClient;
    private final OrderProducer orderProducer;
    private final SerialNumber serialNumber;


    @Transactional
    @Override
    public void orderGifticon(OrderGifticonDto orderGifticonDto) throws Exception {
        Gifticon gifticon = gifticonRepository.findById(orderGifticonDto.getGifticonId()).orElseThrow(() -> new NotExistGifticonException("존재하지 않는 기프티콘입니다."));
        String fromUserNickname = userClient.getUserUserById(orderGifticonDto.getFromUserId()).getData().getNickname();
        System.out.println("fromUserNickname = " + fromUserNickname);
        Long toUserId = userClient.getUserByUserNickname(orderGifticonDto.getToUserNickname()).getData().getUserId();
        System.out.println("toUserId = " + toUserId);


        //TODO 기프티콘 감소 처리
        gifticon.decreaseAmount(orderGifticonDto.getAmount());

        Order order = Order.of(
                gifticon.getId(),
                gifticon.getName(),
                orderGifticonDto.getFromUserId(),
                fromUserNickname,
                toUserId,
                orderGifticonDto.getToUserNickname(),
                gifticon.getPrice(),
                orderGifticonDto.getAmount(),
                orderGifticonDto.getLetter(),
                serialNumber.generateSerialNumber());

        orderRepository.save(order);

    }


    //TODO order-service에서 placed를 받으면 이벤트를 발생시켜 point-service에서 point를 사용하고 성공적으로 사용이된다면
    //TODO 포인트를 감소 시킨 뒤 order-service에게 orderAccepted를 보내준다.
    //TODO 만약, 포인트가 부족하다면 order-service에게 orderRejected를 보내준다. (이때, order-service에서는 order를 취소시킨다.)


    @Transactional
    @Override
    public void orderAccepted(KafkaProduceOrderAcceptedDto kafkaProduceOrderAcceptedDto) throws InterruptedException {
        Order orderAccepted = orderRepository.findById(kafkaProduceOrderAcceptedDto.getId()).orElseThrow(() -> new NotExistGifticonException("존재하지 않는 기프티콘입니다."));
        orderAccepted.changeOrderAcceptedStatus();
        orderRepository.flush();
        orderAccepted.changePreviousOrderAcceptedStatus();

    }

    @Transactional
    @Override
    public void orderRejected(KafkaProduceOrderRejectedDto kafkaProduceOrderAcceptedDto) throws Exception {
        Order orderRejected = orderRepository.findById(kafkaProduceOrderAcceptedDto.getId()).orElseThrow(() -> new NotExistGifticonException("존재하지 않는 기프티콘입니다."));
        orderRejected.changeOrderRejectedStatus();
        //TODO: 기프티콘 재고 증가
        compensationGifticon(orderRejected.getGifticonId(), orderRejected.getAmount());

        //TODO: 기프티콘 선물 보내는 사람에게 실패 알림
        orderProducer.notifyOrderRejected(orderRejected, kafkaProduceOrderAcceptedDto.getHadPoint());


    }

    @Override
    public Page<ReceivedGifticonDto> getReceivedGifticonList(Pageable pageable, Long toUserId) {
//        Sort.Order createdAtDesc = Sort.Order.desc("orderTime");
//        Sort sort = Sort.by(List.of(createdAtDesc));
//        List<Order> orders = orderRepository.findByToUserId(toUserId, sort);
//        if(orders.isEmpty()){
//            return null;
//        }
        Page<Order> receivedOrderHistory = orderRepositoryCustom.getOrderHistoryByToUser(pageable,toUserId);
//        List<ReceivedGifticonDto> receivedGifticonDtos =receivedOrderHistory.getContent()
//                .stream()
//                .map(ReceivedGifticonDto::of)
//                .toList();

        return PageableExecutionUtils.getPage(receivedOrderHistory.stream()
                .map(ReceivedGifticonDto::of)
                .collect(Collectors.toList()), pageable, receivedOrderHistory::getTotalElements);
    }

    @Override
    public Page<SentGifticonDto> getSentGifticonList(Pageable pageable, Long fromUserId) {
        Page<Order> sentOrderHistory = orderRepositoryCustom.getOrderHistoryByFromUser(pageable,fromUserId);
        return PageableExecutionUtils.getPage(sentOrderHistory.stream()
                .map(SentGifticonDto::of)
                .collect(Collectors.toList()), pageable, sentOrderHistory::getTotalElements);
    }

    @Transactional
    @Override
    public void updatedUserForGifticon(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto) {


        List<Order> orderByFromUserId = orderRepository.findOrderByFromUserId(kafkaProduceUpdateUserProfileDto.getUserId());
        if(!orderByFromUserId.isEmpty()){
            for (Order order : orderByFromUserId) {
                order.changeFromUserNickname(kafkaProduceUpdateUserProfileDto.getNickname());
            }
        }

        List<Order> orderByToUserId = orderRepository.findOrderByToUserId(kafkaProduceUpdateUserProfileDto.getUserId());
        if(!orderByToUserId.isEmpty()){
            for (Order order : orderByToUserId) {
                order.changeToUserNickname(kafkaProduceUpdateUserProfileDto.getNickname());
            }
        }


    }

    private void compensationGifticon(Long gifticonId, Long amount) {
        Gifticon gifticon = gifticonRepository.findById(gifticonId)
                .orElseThrow(() -> new NotExistGifticonException("존재하지 않는 기프티콘입니다."));
        gifticon.increaseAmountByCompensation(amount);
    }


}
