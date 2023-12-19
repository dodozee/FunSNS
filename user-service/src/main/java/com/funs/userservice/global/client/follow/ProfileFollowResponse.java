package com.funs.userservice.global.client.follow;

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
