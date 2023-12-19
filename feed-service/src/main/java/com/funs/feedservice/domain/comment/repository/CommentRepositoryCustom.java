package com.funs.feedservice.domain.comment.repository;

import com.funs.feedservice.domain.comment.dto.CommentResponseDto;
import com.funs.feedservice.domain.comment.dto.SearchCommentConditionDto;
import com.funs.feedservice.domain.comment.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentResponseDto> searchCommentList(Long userId, SearchCommentConditionDto searchCommentConditionDto, Comment cursorComment);
}
