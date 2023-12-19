package com.funs.feedservice.domain.feed.web;

import com.funs.feedservice.domain.feed.dto.FeedResultDto;
import com.funs.feedservice.domain.feed.service.FeedService;
import com.funs.feedservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminFeedController {

    private final FeedService feedService;

    //TODO 관리자 전용 타임라인 조회 api
    @GetMapping("/admin/feed/timeline")
    ResponseEntity<Result> adminTimeline(@RequestHeader("user-id")String userId,
                                         @RequestParam("cursor")Long cursorId,
                                         @RequestParam("size")Long size){
        List<FeedResultDto> response = feedService.adminNewsfeed(Long.valueOf(userId), cursorId, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response, "타임라인 조회가 완료되었습니다."));
    }


}
