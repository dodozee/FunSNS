package com.funs.pointservice.domain.dto;

import com.funs.pointservice.domain.entity.PointTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDetailHistoryDto {
    private Long userId;
    private Long balanceAtThatTime;
    private Long amount;
    private Integer type; //0 : 증가, 1 : 감소
    private String description;
    private LocalDateTime createdAt;

    public static PointDetailHistoryDto of(Long userId, Long balanceAtThatTime, Long amount, Integer type, String description, LocalDateTime createdAt) {
        return PointDetailHistoryDto.builder()
                .userId(userId)
                .balanceAtThatTime(balanceAtThatTime)
                .amount(amount)
                .type(type)
                .description(description)
                .createdAt(createdAt)
                .build();
    }

    public static PointDetailHistoryDto of(PointTransaction pointTransaction) {
        return PointDetailHistoryDto.builder()
                .userId(pointTransaction.getUserId())
                .balanceAtThatTime(pointTransaction.getBalanceAtThatTime())
                .amount(pointTransaction.getAmount())
                .type(pointTransaction.getType())
                .description(pointTransaction.getDescription())
                .createdAt(pointTransaction.getTransactionTime())
                .build();
    }

}
