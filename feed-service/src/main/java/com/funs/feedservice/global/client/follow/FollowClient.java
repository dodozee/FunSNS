package com.funs.feedservice.global.client.follow;

import com.funs.feedservice.global.client.user.GetAllUserIdsResponse;
import com.funs.feedservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("Follow-SERVICE")
public interface FollowClient {
    //TODO 본인의 팔로잉 리스트 조회 api

    @GetMapping("/feign/following-list/{userId}")
    Result<GetFollowingListResponse> getUserFollowListByUserId(@PathVariable("userId") String userId);

    @GetMapping("/feign/followed/user/{userId}/feed/{feedUserId}")
    Result<GetFollowedResponse> isFollowed(@PathVariable("userId") String userId, @PathVariable("feedUserId") String feedUserId);


}
