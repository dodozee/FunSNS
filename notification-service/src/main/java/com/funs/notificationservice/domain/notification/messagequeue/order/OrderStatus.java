package com.funs.notificationservice.domain.notification.messagequeue.order;


import lombok.Getter;

@Getter
public enum OrderStatus {
    PLACED("주문신청(결제전)"),
    ACCEPTED("주문완료(결제후)"),
    REJECTED("주문거절(SAGA 진행)");

    private String message;

    OrderStatus(String message) {
        this.message = message;
    }
}
