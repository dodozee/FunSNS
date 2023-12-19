package com.funs.followservice.global.client.user;

import com.funs.followservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface UserClient {
    @GetMapping("/feign/user/{userId}")
    Result<GetUserNicknameResponse> getNicknameUserById(@PathVariable("userId") String userId);

    @GetMapping("/feign/user-profile/{userId}")
    Result<GetUserProfileResponse> getUserProfileById(@PathVariable("userId") String userId);

}
