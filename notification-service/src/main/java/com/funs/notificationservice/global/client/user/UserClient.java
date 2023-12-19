package com.funs.notificationservice.global.client.user;

import com.funs.notificationservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface UserClient {
    @GetMapping("/feign/user/{userId}")
    Result<GetUserNicknameResponse> getNicknameUserById(@PathVariable("userId") String userId);

}
