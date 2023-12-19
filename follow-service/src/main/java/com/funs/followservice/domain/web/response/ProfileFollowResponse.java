package com.funs.followservice.domain.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileFollowResponse {
    private Long userId;
    private Long followerCount;
    private Long followingCount;
}
