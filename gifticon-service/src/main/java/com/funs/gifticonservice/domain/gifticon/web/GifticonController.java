package com.funs.gifticonservice.domain.gifticon.web;


import com.funs.gifticonservice.domain.gifticon.dto.GifticonDto;
import com.funs.gifticonservice.domain.gifticon.service.GifticonService;
import com.funs.gifticonservice.domain.gifticon.web.response.GifticonListResponse;
import com.funs.gifticonservice.domain.gifticon.web.response.GifticonResponse;
import com.funs.gifticonservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GifticonController {

    private final GifticonService gifticonService;

    //TODO 기프티콘 목록 조회

    @GetMapping("/gifticon/category/{categoryName}")
    ResponseEntity<Result> getGifticonList(@PathVariable("categoryName") String categoryName,
                                           @PageableDefault Pageable pageable){
        System.out.println("기프티콘 목록 조회 요청 api");
        Page<GifticonDto> gifticonDtoList = gifticonService.findGifticonList(categoryName, pageable);

        List<GifticonResponse> gifticonList = gifticonDtoList
                .stream()
                .map(GifticonResponse::new)
                .toList();

        GifticonListResponse gifticonListResponse = new GifticonListResponse(
                gifticonList,
                gifticonDtoList.getNumber(),
                gifticonDtoList.getTotalPages()
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(gifticonListResponse));
    }








}
