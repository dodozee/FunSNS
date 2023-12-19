package com.funs.feedservice.domain.comment.service;

import com.funs.feedservice.domain.comment.dto.*;

import java.util.List;

public interface CommentService {
    void addComment(Long userId, Long feedId, AddCommentDto addCommentDto);

    void replyComment(Long userId, Long commentId, ReplyCommentDto replyCommentDto);

    void updateCommentOrReply(Long userId, Long commentId, UpdateCommentDto updateCommentDto);

    void deleteCommentOrReply(Long userId, Long commentId);
    void likeComment(Long userId, Long commentId);
    void unlikeComment(Long userId, Long commentId);
    List<ForListInfoResponseDto> commentLikeList(Long userId, Long commentId);

    List<CommentResponseDto> getCommentList(SearchCommentConditionDto searchCommentConditionDto, Long userId, Long cursorId);
}
