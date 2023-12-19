package com.funs.userservice.domain.user.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequest {
    private String nickname; // 닉네임
    private String introduction; // 자기소개
    private String userType; // 사용자 타입("연구자", "후원자")
}
