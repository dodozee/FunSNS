package com.funs.feedservice.domain.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@Data
public class CommentResponseDto {

    private Long commentId; //댓글 아이디

    private String content; //댓글 내용

    private Integer likeCount; ///좋아요 수

    private Integer replyCount; //댓글의 댓글 수

    private Long userId; //댓글 작성자 아이디

    private String nickname; //댓글 작성자 닉네임

    private String profileImgURL; //댓글 작성자 프로필 이미지

    private Boolean isLiked; //해당 사용자 입장에서 좋아요 여부

    private LocalDateTime createdDate; //댓글 생성 날짜

    @QueryProjection
    public CommentResponseDto(Long commentId, String content, Integer likeCount, Integer replyCount, Long userId, String nickname, Boolean isLiked, LocalDateTime createdDate) {
        this.commentId = commentId;
        this.content = content;
        this.likeCount = likeCount;
        this.userId = userId;
        this.nickname = nickname;
        this.replyCount = replyCount;
        this.isLiked = isLiked;
        this.createdDate = createdDate;
    }
}
