package com.funs.notificationservice.domain.notification.repository;

import com.funs.notificationservice.domain.notification.entity.Notification;
import com.funs.notificationservice.global.dto.Yn;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId, Sort sort);

    Long countByUserIdAndReadYn(Long userId, Yn readYn);

}
