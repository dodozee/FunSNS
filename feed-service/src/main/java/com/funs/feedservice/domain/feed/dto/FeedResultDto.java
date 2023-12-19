package com.funs.feedservice.domain.feed.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FeedResultDto {
    private Long feedId; //피드 아이디
    private Long userId; //피드 올린 사용자 id
    private String nickname; //닉네임
    private String title; //제목
    private String content; //내용
    private String profileImgURL;//피드 올린 사람 프로필 이미지
    private String feedImgURL; //피드 이미지
    private Integer likeCount; //좋아요 수
    private Integer commentCount; //댓글 수
    private Boolean isLiked; //해당 사용자 입장에서 좋아요 여부
    private LocalDateTime createdDate; //피드 생성 날짜

    @QueryProjection
    @Builder
    public FeedResultDto(Long feedId, Long userId, String nickname, String title, String content, String feedImgURL, Integer likeCount, Integer commentCount, Boolean isLiked, LocalDateTime createdDate) {
        this.feedId = feedId; //
        this.userId = userId; //
        this.nickname = nickname; //
        this.title = title; //
        this.content = content; //
        this.feedImgURL = feedImgURL; //
        this.likeCount = likeCount; //
        this.commentCount = commentCount;//
        this.isLiked = isLiked;
        this.createdDate = createdDate;
    }

}
