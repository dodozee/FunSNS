package com.funs.feedservice.domain.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailFeedResponseDto {

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
    private Boolean isMine; //해당 사용자가 피드 작성자인지 여부
    private Boolean isFollowed; //해당 사용자가 피드 작성자를 팔로우 하고 있는지 여부
    private LocalDateTime createdDate; //피드 생성 날짜
    private Long viewCount; //조회수
}
