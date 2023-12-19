package com.funs.userservice.global.client.follow;

import com.funs.userservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("FOLLOW-SERVICE")
public interface FollowClient {
    @GetMapping("/feign/profile/follow/count/{userId}")
    Result<ProfileFollowResponse> getFollowerCount(@PathVariable("userId") String userId);

    @GetMapping("/feign/available/follow/{fromUserId}/{toUserId}")
    boolean getAvailableFollow(@PathVariable("fromUserId") String fromUserId,@PathVariable("toUserId") String toUserId);
}
