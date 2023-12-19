package com.funs.feedservice.domain.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ForListInfoResponseDto {

    private Long cursorId; //커서 아이디
    private Long userId; //유저 아이디
    private String nickname; //닉네임
    private String profileImgURL; //프로필 이미지 주소
    private boolean isFollowed = false; //이 사용자와 나와의 팔로우 여부


    @QueryProjection
    public ForListInfoResponseDto(Long cursorId, Long userId, String nickname) {
        this.cursorId = cursorId;
        this.userId = userId;
        this.nickname = nickname;
    }


}
