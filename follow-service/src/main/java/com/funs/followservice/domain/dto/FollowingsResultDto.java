package com.funs.followservice.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowingsResultDto {
    private Long userId; // 유저Id
    private String nickname; // 닉네임
    private String profileImage; // 프로필 이미지
    private boolean isFollowed = false; // 이 사용자와 나와의 팔로우 여부


    @QueryProjection
    @Builder
    public FollowingsResultDto(Long userId,String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

}
