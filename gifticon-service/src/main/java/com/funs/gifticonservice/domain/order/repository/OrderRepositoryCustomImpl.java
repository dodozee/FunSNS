package com.funs.gifticonservice.domain.order.repository;


import com.funs.gifticonservice.domain.order.entity.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.funs.gifticonservice.domain.order.entity.QOrder.order;


@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Order> getOrderHistoryByFromUser(Pageable pageable, Long fromUserId){

        Long count = queryFactory.select(order.count())
                .from(order)
                .where(order.fromUserId.eq(fromUserId))
                .fetchOne();


        List<Order> orderList = queryFactory
                .selectFrom(order)
                .where(order.fromUserId.eq(fromUserId))
                .orderBy(order.orderTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return PageableExecutionUtils.getPage(orderList, pageable, () -> count);

    }
//
    @Override
    public Page<Order> getOrderHistoryByToUser(Pageable pageable, Long toUserId) {

        Long count = queryFactory.select(order.count())
                .from(order)
                .where(order.toUserId.eq(toUserId))
                .fetchOne();




        List<Order> orderList = queryFactory
                .selectFrom(order)
                .where(order.toUserId.eq(toUserId))
                .orderBy(order.orderTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(orderList, pageable, () -> count);


    }
}
