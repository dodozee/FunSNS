package com.funs.gifticonservice.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@EntityListeners(value = {OrderListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @Column(name = "gifticon_id")
    private Long gifticonId;
    @Column(name = "gifticon_name")
    private String gifticonName;
    @Column(name = "from_user_id")
    private Long fromUserId;
    @Column(name = "from_user_nickname")
    private String fromUserNickname;
    @Column(name = "to_user_id")
    private Long toUserId;
    @Column(name = "to_user_nickname")
    private String toUserNickname;
    private Long price;
    private Long amount;
    @Column(name = "order_time")
    private LocalDateTime orderTime;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "previous_order_status")
    private PreviousOrderStatus previousOrderStatus;
    @Column(name = "serial_number")
    private String serialNumber;
    private String letter;
    @Column(name = "is_used")
    private boolean isUsed;
//    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Gifticon gifticon;


    public static Order of(Long gifticonId,
                           String gifticonName,
                           Long fromUserId,
                           String fromUserNickname,
                           Long toUserId,
                           String toUserNickname,
                           Long price,
                           Long amount,
                           String letter,
                           String serialNumber) {
        Order order = new Order();
        order.gifticonId = gifticonId;
        order.gifticonName = gifticonName;
        order.fromUserId = fromUserId;
        order.fromUserNickname = fromUserNickname;
        order.toUserId = toUserId;
        order.toUserNickname = toUserNickname;
        order.price = price;
        order.amount = amount;
        order.orderTime = LocalDateTime.now();
        order.orderStatus = OrderStatus.PLACED;
        order.previousOrderStatus = PreviousOrderStatus.PLACED;
        order.letter = letter;
        order.serialNumber= serialNumber;
        return order;
    }
    public void changeOrderPlacedStatus() {
        this.orderStatus = OrderStatus.PLACED;
    }

    public void changeOrderAcceptedStatus() {
        this.orderStatus = OrderStatus.ACCEPTED;
        this.previousOrderStatus= PreviousOrderStatus.PLACED;
    }

    public void changeOrderRejectedStatus() {
        this.orderStatus = OrderStatus.REJECTED;
    }


    public void changeOrderCompletedStatus() {
        this.orderStatus = OrderStatus.COMPLETED;
    }
    //TODO: 주문 취소시 상태 변경 로직 추가해야함
    public void changeOrderCanceledStatus() {
        this.orderStatus = OrderStatus.CANCELED;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void changeFromUserNickname(String nickname) {
        this.fromUserNickname = nickname;
    }

    public void changeToUserNickname(String nickname) {
        this.toUserNickname = nickname;
    }

    public void changePreviousOrderAcceptedStatus() {
        this.previousOrderStatus = PreviousOrderStatus.ACCEPTED;
    }
}
