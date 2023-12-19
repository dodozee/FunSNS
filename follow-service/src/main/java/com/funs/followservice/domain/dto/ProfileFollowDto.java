package com.funs.followservice.domain.dto;

import com.funs.followservice.domain.web.response.ProfileFollowResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileFollowDto {
    private Long userId;
    private Long followerCount;
    private Long followingCount;

    public ProfileFollowResponse toResponse() {
        return ProfileFollowResponse.builder()
                .userId(userId)
                .followerCount(followerCount)
                .followingCount(followingCount)
                .build();
    }
}
