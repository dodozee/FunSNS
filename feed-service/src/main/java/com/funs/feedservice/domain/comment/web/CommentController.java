package com.funs.feedservice.domain.comment.web;


import com.funs.feedservice.domain.comment.dto.*;
import com.funs.feedservice.domain.comment.service.CommentService;
import com.funs.feedservice.domain.comment.service.CommentTargetType;
import com.funs.feedservice.domain.comment.web.request.AddCommentRequest;
import com.funs.feedservice.global.dto.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/feed/{feedId}/comment")
    ResponseEntity<Result> getCommentListFromFeed(@RequestHeader("user-id")String userId,
                                              @PathVariable("feedId") Long feedId,
                                              @RequestParam(value="cursor") Long cursorId,
                                              @RequestParam(value="size") Long size){

        SearchCommentConditionDto searchCommentConditionDto = SearchCommentConditionDto.builder()
                .targetType(CommentTargetType.FEED)
                .targetId(feedId)
                .size(size)
                .build();
        List<CommentResponseDto> response = commentService.getCommentList(searchCommentConditionDto,Long.valueOf(userId), cursorId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response,"댓글 리스트 조회가 완료되었습니다."));

    }


    //TODO 댓글 작성 api
    @PostMapping("/feed/{feedId}/comment")
    ResponseEntity<Result> addComment(@RequestHeader("user-id")String userId,
                                      @PathVariable("feedId") Long feedId,
                                      @RequestBody AddCommentRequest content){
        AddCommentDto addCommentDto = new AddCommentDto(content.getContent());
        commentService.addComment(Long.valueOf(userId), feedId, addCommentDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("댓글 작성이 완료되었습니다."));

    }

    //TODO 대댓글 작성 api
    @PostMapping("/feed/comment/{commentId}/reply")
    ResponseEntity<Result> replyComment(@RequestHeader("user-id")String userId,
                                        @PathVariable("commentId") Long commentId,
                                        @RequestBody ReplyCommentDto content){
        ReplyCommentDto replyCommentDto = new ReplyCommentDto(content.getContent());
        commentService.replyComment(Long.valueOf(userId), commentId, replyCommentDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("대댓글 작성이 완료되었습니다."));

    }

    //TODO 댓글 수정 api
    @PutMapping("/feed/comment/{commentId}")
    ResponseEntity<Result> updateComment(@RequestHeader("user-id")String userId,
                                         @PathVariable("commentId") Long commentId,
                                         @RequestBody UpdateCommentDto content){
        UpdateCommentDto updateCommentDto = new UpdateCommentDto(content.getContent());
        commentService.updateCommentOrReply(Long.valueOf(userId), commentId, updateCommentDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("댓글 수정이 완료되었습니다."));

    }

    //TODO 댓글 삭제 api
    @DeleteMapping("/feed/comment/{commentId}")
    ResponseEntity<Result> deleteCommentAndReply(@RequestHeader("user-id")String userId,
                                                 @PathVariable("commentId") Long commentId){
        commentService.deleteCommentOrReply(Long.valueOf(userId), commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("댓글 삭제가 완료되었습니다."));

    }

    //TODO 댓글 좋아요 api
    @PostMapping("/feed/comment/{commentId}/like")
    ResponseEntity<Result> likeComment(@RequestHeader("user-id")String userId,
                                       @PathVariable("commentId") Long commentId){
        commentService.likeComment(Long.valueOf(userId), commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("댓글 좋아요가 완료되었습니다."));

    }

    //TODO 댓글 좋아요 취소 api
    @DeleteMapping("/feed/comment/{commentId}/like")
    ResponseEntity<Result> unlikeComment(@RequestHeader("user-id")String userId,
                                         @PathVariable("commentId") Long commentId){
        commentService.unlikeComment(Long.valueOf(userId), commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("댓글 좋아요 취소가 완료되었습니다."));

    }

    //TODO 댓글 좋아요 리스트 api
    @GetMapping("/feed/comment/{commentId}/like-list")
    ResponseEntity<Result> commentLikedUserList(@RequestHeader("user-id")String userId,
                                                @PathVariable("commentId") Long commentId){
        List<ForListInfoResponseDto> response = commentService.commentLikeList(Long.valueOf(userId), commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response,"댓글 좋아요 리스트 조회가 완료되었습니다."));

    }



}
