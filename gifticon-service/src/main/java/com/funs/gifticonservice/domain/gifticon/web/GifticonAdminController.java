package com.funs.gifticonservice.domain.gifticon.web;


import com.funs.gifticonservice.domain.gifticon.dto.GifticonDto;
import com.funs.gifticonservice.domain.gifticon.dto.RegisterGifticonDto;
import com.funs.gifticonservice.domain.gifticon.dto.UpdateGifticonDto;
import com.funs.gifticonservice.domain.gifticon.service.GifticonService;
import com.funs.gifticonservice.domain.gifticon.web.requset.RegisterGifticonRequest;
import com.funs.gifticonservice.domain.gifticon.web.requset.UpdateGifticonRequest;
import com.funs.gifticonservice.domain.gifticon.web.response.GifticonResponse;
import com.funs.gifticonservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class GifticonAdminController {

    private final GifticonService gifticonService;

    @PostMapping("/gifticon")
    ResponseEntity<Result> createGifticon(@RequestHeader("user-id") String userId,
                                          @RequestPart(value = "registerGifticonRequest") RegisterGifticonRequest registerGifticonRequest,
                                          @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        RegisterGifticonDto registerGifticonDto = RegisterGifticonDto.builder()
                .categoryName(registerGifticonRequest.getCategoryName())
                .gifticonName(registerGifticonRequest.getGifticonName())
                .price(registerGifticonRequest.getPrice())
                .description(registerGifticonRequest.getDescription())
                .image(image)
                .amount(registerGifticonRequest.getAmount())
                .build();
        gifticonService.createGifticon(Long.valueOf(userId), registerGifticonDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.createSuccessResult("기프티콘 등록이 완료되었습니다."));

    }

    @PutMapping("/gifticon/{gifticonId}")
    ResponseEntity<Result> updateGifticon(@RequestHeader("user-id") String userId,
                                          @PathVariable String gifticonId,
                                          @RequestPart(value = "updateGifticonRequest") UpdateGifticonRequest updateGifticonRequest,
                                          @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        UpdateGifticonDto updateGifticonDto = UpdateGifticonDto.builder()
                .categoryName(updateGifticonRequest.getCategoryName())
                .gifticonName(updateGifticonRequest.getGifticonName())
                .price(updateGifticonRequest.getPrice())
                .description(updateGifticonRequest.getDescription())
                .image(image)
                .amount(updateGifticonRequest.getAmount())
                .build();
        gifticonService.updateGifticon(Long.valueOf(userId), Long.valueOf(gifticonId), updateGifticonDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("기프티콘 수정이 완료 되었습니다."));
    }

    @DeleteMapping("/gifticon/{gifticonId}")
    ResponseEntity<Result> deleteGifticon(@RequestHeader("user-id") String userId,
                                          @PathVariable String gifticonId) {
        gifticonService.deleteGifticon(Long.valueOf(userId), Long.valueOf(gifticonId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("기프티콘 삭제가 완료 되었습니다."));
    }

    //TODO 기프티콘Id로 단건 조회
    @GetMapping("/gifticon/{gifticonId}")
    ResponseEntity<Result> getGifticon(@PathVariable String gifticonId) {
        System.out.println("기프티콘 상세 조회 요청 api");
        GifticonDto gifticonDto = gifticonService.getGifticon(Long.valueOf(gifticonId));
        GifticonResponse gifticonResponse = new GifticonResponse(gifticonDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(gifticonResponse,"기프티콘 상세 조회가 완료 되었습니다."));
    }


}
