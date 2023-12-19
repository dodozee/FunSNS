package com.funs.followservice.domain.service;

import com.funs.followservice.domain.dto.FollowDto;
import com.funs.followservice.domain.dto.FollowersResultDto;
import com.funs.followservice.domain.dto.FollowingsResultDto;
import com.funs.followservice.domain.dto.ProfileFollowDto;
import com.funs.followservice.domain.web.response.GetFollowingListResponse;

import java.util.List;

public interface FollowService {
    void follow(FollowDto followDto);

    void unfollow(FollowDto followDto);

    ProfileFollowDto getFollowerCount(Long userId);

    boolean getAvailableFollow(Long fromUserId, Long toUserId);

    GetFollowingListResponse getUserFollowListByUserId(Long userId);

    boolean isFollowed(Long userId, Long feedUserId);
    List<FollowersResultDto> getFollowers(Long userId, Long searchUserId, Long cursorId, Long size);
    List<FollowingsResultDto> getFollowings(Long userId, Long searchUserId,  Long cursorId, Long size);

}
