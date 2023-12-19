package com.funs.notificationservice.domain.notification.service;

import com.funs.notificationservice.domain.notification.dto.FindNotificationDto;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceCreatedFeedDto;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceFollowDto;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceNotificationOrderAcceptedDto;
import com.funs.notificationservice.domain.notification.dto.Producer.KafkaProduceNotificationOrderRejectedDto;
import com.funs.notificationservice.domain.notification.dto.UpdateNotificationDto;
import com.funs.notificationservice.domain.notification.entity.Notification;
import com.funs.notificationservice.domain.notification.exception.NotExistNotification;
import com.funs.notificationservice.domain.notification.repository.NotificationRepository;
import com.funs.notificationservice.global.client.user.UserClient;
import com.funs.notificationservice.global.dto.Yn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserClient userClient;

    @Override
    public List<FindNotificationDto> findNotificationByUserId(Long id) {
        Sort.Order readYnAsc = Sort.Order.asc("readYn");
        Sort.Order createdAtDesc = Sort.Order.desc("createdAt");

        Sort sort = Sort.by(List.of(readYnAsc, createdAtDesc));
        return notificationRepository.findByUserId(id, sort)
                .stream()
                .map(FindNotificationDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateNotification(UpdateNotificationDto dto) {
        Notification notification = notificationRepository.findById(dto.getId())
                .orElseThrow(() -> new NotExistNotification(dto.getId() + "는 없는 알림 고유번호입니다."));

        Yn readYn = dto.isRead() ? Yn.Y : Yn.N;
        notification.updateReadYn(readYn);
    }

    @Override
    public Long findNotificationCounts(Long userId, Yn readYn) {
        return notificationRepository.countByUserIdAndReadYn(userId, readYn);
    }

    @Transactional
    @Override
    public void notifyByOrderAccepted(KafkaProduceNotificationOrderAcceptedDto kafkaProduceNotificationOrderAcceptedDto) {
        Long toUserId = kafkaProduceNotificationOrderAcceptedDto.getToUserId();
        Long fromUserId = kafkaProduceNotificationOrderAcceptedDto.getFromUserId();
        String gifticonName = kafkaProduceNotificationOrderAcceptedDto.getGifticonName();
        String toUserNickname = kafkaProduceNotificationOrderAcceptedDto.getToUserNickname();
        String fromUserNickname = kafkaProduceNotificationOrderAcceptedDto.getFromUserNickname();

        String title = "선물 완료";
        String message = "[ " +fromUserNickname+ " ]님이 [ "+ toUserNickname +" ]님에게 보내 주신"+ " [" + gifticonName + "] 기프티콘 선물이 완료되었습니다.";
        Notification notification = Notification.of(fromUserId, message, title);
        notificationRepository.save(notification);

        String title2 = "선물 받음";
        String message2 = "[ " +toUserNickname+ " ]님이 [ "+ fromUserNickname +" ]님에게 받은"+ " [" + gifticonName + "] 기프티콘 선물을 받았습니다.";
        Notification notification2 = Notification.of(toUserId, message2, title2);
        notificationRepository.save(notification2);


    }

    @Transactional
    @Override
    public void notifyOrderRejected(KafkaProduceNotificationOrderRejectedDto kafkaProduceNotificationOrderRejectedDto) {
        Long toUserId = kafkaProduceNotificationOrderRejectedDto.getToUserId();
        Long fromUserId = kafkaProduceNotificationOrderRejectedDto.getFromUserId();
        String gifticonName = kafkaProduceNotificationOrderRejectedDto.getGifticonName();
        String toUserNickname = kafkaProduceNotificationOrderRejectedDto.getToUserNickname();
        String fromUserNickname = kafkaProduceNotificationOrderRejectedDto.getFromUserNickname();

        String title = "주문 거절";
        String message = "[ " +fromUserNickname+ " ]님이 [ "+ toUserNickname +" ]님에게 보내 줄"+ " [" + gifticonName + "] 기프티콘 주문이 거절되었습니다." +  "사유- 포인트 부족, 현재 포인트 : [" + kafkaProduceNotificationOrderRejectedDto.getHadPoint() + "]";
        Notification notification = Notification.of(fromUserId, message, title);
        notificationRepository.save(notification);

    }

    @Transactional
    @Override
    public void notifyByFollow(KafkaProduceFollowDto kafkaProduceFollowDto) {
        Long toUserId = kafkaProduceFollowDto.getToUserId();
        String toUserNickname = kafkaProduceFollowDto.getToUserNickname();
        String fromUserNickname = kafkaProduceFollowDto.getFromUserNickname();

        String title = "팔로우";
        String message = "[ " +fromUserNickname+ " ]님이 [ "+ toUserNickname +" ]님을 팔로우 하였습니다.";
        Notification notification = Notification.of(toUserId, message, title);
        notificationRepository.save(notification);

    }

    @Transactional
    @Override
    public void notifyByCreatedFeedDto(KafkaProduceCreatedFeedDto kafkaProduceCreatedFeedDto) {
        Long userId = kafkaProduceCreatedFeedDto.getUserId();
        String title = "포인트 획득";
        String message = "[ " + kafkaProduceCreatedFeedDto.getTitle() + " ] 피드를 작성하여서 포인트 100점을 획득하였습니다.";
        Notification notification = Notification.of(userId, message, title);
        notificationRepository.save(notification);
    }


}
