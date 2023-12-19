package com.funs.gifticonservice.domain.order.entity;


import com.funs.gifticonservice.domain.order.exception.OrderException;
import com.funs.gifticonservice.domain.order.service.OrderProducer;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderListener {

    @Lazy
    @Autowired
    private OrderProducer orderProducer;


    @PostUpdate
    public void postUpdate(Order order){
        OrderStatus orderStatus = order.getOrderStatus();
        log.info("[OrderListener] {}", orderStatus.name());
        if (orderStatus == OrderStatus.PLACED) {
            try{
                orderProducer.orderPlaced(order);
            }catch (Exception ex){
                throw new OrderException(ex.getMessage());
            }

        } else if (orderStatus == OrderStatus.ACCEPTED && order.getPreviousOrderStatus() == PreviousOrderStatus.PLACED) {
            try {
                //TODO 선물 보낸 사람에게는 선물 성공적으로 보냈다 알림 메시지
                //TODO 선물 받은 사람에게는 선물 받았다 알림 메시지
                orderProducer.notifyByOrderAccepted(order);
                //TODO OrderStatus 완료 업데이트 메서드 실행
            } catch (Exception ex) {
                throw new OrderException(ex.getMessage());
            }
        }
//        else if (orderStatus == OrderStatus.REJECTED) {
//            try {
//                //TODO 선물 보낸 사람에게는 선물 실패했다는 알림 메시지
//                orderProducer.notifyOrderRejected(order, );
//                //TODO OrderStatus 실패 업데이트 메서드 실행
//            } catch (Exception ex) {
//                throw new OrderException(ex.getMessage());
//            }
//        }
    }


}
