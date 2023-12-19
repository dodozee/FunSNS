package com.funs.gifticonservice.domain.order.web;

import com.funs.gifticonservice.domain.order.dto.*;
import com.funs.gifticonservice.domain.order.service.OrderService;
import com.funs.gifticonservice.domain.order.web.request.OrderGifticonRequest;
import com.funs.gifticonservice.domain.order.web.response.ReceivedGifticonHistoryResponse;
import com.funs.gifticonservice.domain.order.web.response.SentGifticonHistoryResponse;
import com.funs.gifticonservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //TODO 기프티콘 구매(선물) 주문 요청
    @PostMapping("/order/gifticon")
    ResponseEntity<Result> orderGifticon(@RequestHeader("user-id") String userId,
                                         @RequestBody OrderGifticonRequest orderGifticonRequest) throws Exception {
        System.out.println("기프티콘 구매 요청 api");
        OrderGifticonDto orderGifticonDto = OrderGifticonDto.of(orderGifticonRequest, Long.valueOf(userId));
        orderService.orderGifticon(orderGifticonDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Result.createSuccessResult("기프티콘 구매 요청이 완료되었습니다. 성공 및 실패는 알림으로 전송됩니다."));
    }

    //TODO 받은 기프티콘 조회
    @GetMapping("/order/gifticon/list/received")
    ResponseEntity<Result> getGifticonList(@RequestHeader("user-id") String toUserId,
                                           @PageableDefault Pageable pageable){
        System.out.println("받은 기프티콘 조회 요청 api");
        Page<ReceivedGifticonDto> receivedGifticonDtosList = orderService.getReceivedGifticonList(pageable, Long.valueOf(toUserId));

        List<ReceivedGifticon> orderList = receivedGifticonDtosList.stream()
                .map(ReceivedGifticon::of)
                .toList();

        ReceivedGifticonHistoryResponse receivedGifticonHistoryResponse = new ReceivedGifticonHistoryResponse(
                orderList,
                receivedGifticonDtosList.getNumber(),
                receivedGifticonDtosList.getTotalPages()
        );


        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(receivedGifticonHistoryResponse));
    }

    //TODO 보낸 기프티콘 조회
    @GetMapping("/order/gifticon/list/sent")
    ResponseEntity<Result> getSentGifticonList(@RequestHeader("user-id") String fromUserId,
                                               @PageableDefault Pageable pageable){
        System.out.println("보낸 기프티콘 조회 요청 api");
        Page<SentGifticonDto> sentGifticonDtosList = orderService.getSentGifticonList(pageable, Long.valueOf(fromUserId));

        List<SentGifticon> orderList = sentGifticonDtosList.stream()
                .map(SentGifticon::of)
                .toList();

        SentGifticonHistoryResponse sentGifticonHistoryResponse = new SentGifticonHistoryResponse(
                orderList,
                sentGifticonDtosList.getNumber(),
                sentGifticonDtosList.getTotalPages()
                );



        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(sentGifticonHistoryResponse));
    }


}
