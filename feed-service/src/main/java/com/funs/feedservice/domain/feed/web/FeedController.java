package com.funs.feedservice.domain.feed.web;


import com.funs.feedservice.domain.comment.dto.ForListInfoResponseDto;
import com.funs.feedservice.domain.feed.dto.DetailFeedResponseDto;
import com.funs.feedservice.domain.feed.dto.FeedResultDto;
import com.funs.feedservice.domain.feed.service.FeedService;
import com.funs.feedservice.domain.feed.web.request.CreateFeedRequest;
import com.funs.feedservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    //TODO 피드 작성 api
    @PostMapping("/feed")
    ResponseEntity<Result> createFeed(@RequestHeader("user-id")String userId,
                                      @RequestPart(value = "content") CreateFeedRequest content,
                                      @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        feedService.createFeed(Long.valueOf(userId), content, image);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("피드 작성이 완료되었습니다."));

    }

    //TODO 피드 단건 조회 api : 뉴스피드나 타임라인에서 피드를 클릭했을 때
    @GetMapping("/feed/{feedId}")
    ResponseEntity<Result> feedDetail(@RequestHeader("user-id")String userId,
                                      @PathVariable("feedId") Long feedId){

        DetailFeedResponseDto response = feedService.feedDetail(Long.valueOf(userId), feedId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response, "피드 상세 조회가 완료되었습니다."));

    }

    //TODO 피드 수정 api
    @PutMapping("/feed/{feedId}")
    ResponseEntity<Result> modifyFeed(@RequestHeader("user-id")String userId,
                                      @PathVariable("feedId") Long feedId,
                                      @RequestPart(value = "content") CreateFeedRequest content,
                                      @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        feedService.modifyFeed(Long.valueOf(userId), feedId, content, image);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("피드 수정이 완료되었습니다."));

    }

    //TODO 피드 삭제 api
    @DeleteMapping("/feed/{feedId}")
    ResponseEntity<Result> deleteFeed(@RequestHeader("user-id")String userId,
                                      @PathVariable("feedId") Long feedId){

        feedService.deleteFeed(Long.valueOf(userId), feedId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("피드 삭제가 완료되었습니다."));

    }


    //TODO 피드 좋아요 api
    @PostMapping("/feed/{feedId}/like")
    ResponseEntity<Result> likeFeed(@RequestHeader("user-id")String userId,
                                    @PathVariable("feedId") Long feedId){

        feedService.likeFeed(Long.valueOf(userId), feedId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("피드 좋아요가 완료되었습니다."));

    }

    //TODO 피드 좋아요 취소 api
    @DeleteMapping("/feed/{feedId}/like")
    ResponseEntity<Result> unlikeFeed(@RequestHeader("user-id")String userId,
                                      @PathVariable("feedId") Long feedId){

        feedService.unlikeFeed(Long.valueOf(userId), feedId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("피드 좋아요 취소가 완료되었습니다."));

    }

    //TODO 피드 좋아요 리스트 api
    @GetMapping("/feed/{feedId}/like-list")
    ResponseEntity<Result> feedLikeList(@RequestHeader("user-id")String userId,
                                        @PathVariable("feedId") Long feedId,
                                        @RequestParam(value = "cursor") Long cursorId,
                                        @RequestParam(value = "size") Long size){

        List<ForListInfoResponseDto> response = feedService.feedLikeList(Long.valueOf(userId), feedId, cursorId, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response, "피드 좋아요 리스트 조회가 완료되었습니다."));

    }

    //TODO 뉴스피드 조회 api
    @GetMapping("/feed/newsfeed")
    ResponseEntity<Result> newsfeed(@RequestHeader("user-id")String userId,
                                    @RequestParam(value = "cursor") Long cursorId,
                                    @RequestParam(value = "size") Long size){

        List<FeedResultDto> response = feedService.newsfeed(Long.valueOf(userId), cursorId, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response, "타임라인 조회가 완료되었습니다."));

    }

    //TODO 타임라인 조회 api
    @GetMapping("/feed/timeline")
    ResponseEntity<Result> timeline(@RequestHeader("user-id")String userId,
                                    @RequestParam(value = "cursor") Long cursorId,
                                    @RequestParam(value = "size") Long size){

        List<FeedResultDto> response = feedService.timeline(Long.valueOf(userId), cursorId, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response, "타임라인 조회가 완료되었습니다."));

    }
}
