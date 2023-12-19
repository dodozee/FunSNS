package com.funs.pointservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDetailHistory {
    private Long userId; //사용자 id
    private Long balanceAtThatTime; //거래 발생 해당 시점의 잔액
    private Long amount; //거래 금액
    private Integer type; //0 : 증가, 1 : 감소
    private String description; //거래 내역
    private LocalDateTime createdAt; //거래 발생 시간

    public static PointDetailHistory of(PointDetailHistoryDto pointDetailHistoryDto) {
        return PointDetailHistory.builder()
                .userId(pointDetailHistoryDto.getUserId())
                .balanceAtThatTime(pointDetailHistoryDto.getBalanceAtThatTime())
                .amount(pointDetailHistoryDto.getAmount())
                .type(pointDetailHistoryDto.getType())
                .description(pointDetailHistoryDto.getDescription())
                .createdAt(pointDetailHistoryDto.getCreatedAt())
                .build();
    }
//
//    public static PointDetailHistory of(Long userId, Long balanceAtThatTime, Long amount, Integer type, String description, LocalDateTime createdAt) {
//        return PointDetailHistory.builder()
//                .userId(userId)
//                .balanceAtThatTime(balanceAtThatTime)
//                .amount(amount)
//                .type(type)
//                .description(description)
//                .createdAt(createdAt)
//                .build();
//    }
//
//    public static PointDetailHistory of(PointTransaction pointTransaction) {
//        return PointDetailHistory.builder()
//                .userId(pointTransaction.getUserId())
//                .balanceAtThatTime(pointTransaction.getBalanceAtThatTime())
//                .amount(pointTransaction.getAmount())
//                .type(pointTransaction.getType())
//                .description(pointTransaction.getDescription())
//                .createdAt(pointTransaction.getTransactionTime())
//                .build();
//    }

}
