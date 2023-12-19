package com.funs.pointservice.domain.repository;

import com.funs.pointservice.domain.entity.PointTransaction;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.funs.pointservice.domain.entity.QPointTransaction.pointTransaction;


@Repository
@RequiredArgsConstructor
public class PointTransactionRepositoryCustomImpl implements PointTransactionRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PointTransaction> getPointTransactionHistoryByUserId(Long userId, Pageable pageable) {

        Long count = queryFactory.select(pointTransaction.count())
                .from(pointTransaction)
                .where(pointTransaction.userId.eq(userId))
                .fetchOne();

        List<PointTransaction> pointTransactionList = queryFactory
                .selectFrom(pointTransaction)
                .where(pointTransaction.userId.eq(userId))
                .orderBy(pointTransaction.transactionTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return PageableExecutionUtils.getPage(pointTransactionList, pageable, () -> count);
    }
}
