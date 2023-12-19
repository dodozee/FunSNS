package com.funs.gifticonservice.domain.order.repository;

import com.funs.gifticonservice.domain.order.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.toUserId = :toUserId")
    List<Order> findByToUserId(@Param("toUserId") Long toUserId, Sort sort);

    @Query("select o from Order o where o.fromUserId = :fromUserId")
    List<Order> findByFromUserId(Long fromUserId, Sort sort);

    @Query("select o from Order o where o.toUserId = :userId")
    List<Order> findOrderByFromUserId(Long userId);

    @Query("select o from Order o where o.fromUserId = :userId")
    List<Order> findOrderByToUserId(Long userId);
}
