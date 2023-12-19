package com.funs.pointservice.domain.entity;


import com.funs.pointservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "point_transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointTransaction extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "point_transaction_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private Integer type; //0: 증가, 1: 감소

    private String description;

    private Long amount;

    @Column(name = "balance_at_that_time")
    private Long balanceAtThatTime;
    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    public void setPoint(Point point) {
        this.point = point;
    }

    public static PointTransaction init(Long userId, Integer type, String description, Long amount, Long balanceAtThatTime) {
        PointTransaction pointTransaction = new PointTransaction();
        pointTransaction.userId = userId;
        pointTransaction.type = type;
        pointTransaction.description = description;
        pointTransaction.amount = amount;
        pointTransaction.balanceAtThatTime = balanceAtThatTime;
        pointTransaction.transactionTime = LocalDateTime.now();
        return pointTransaction;
    }

    public static PointTransaction of(Long userId, Integer type, String description, Long amount, Long balanceAtThatTime) {
        PointTransaction pointTransaction = new PointTransaction();
        pointTransaction.userId = userId;
        pointTransaction.type = type;
        pointTransaction.description = description;
        pointTransaction.amount = amount;
        pointTransaction.balanceAtThatTime = balanceAtThatTime;
        pointTransaction.transactionTime = LocalDateTime.now();
        return pointTransaction;
    }
}
