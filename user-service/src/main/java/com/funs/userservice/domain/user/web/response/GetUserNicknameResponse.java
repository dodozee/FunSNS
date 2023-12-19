package com.funs.userservice.domain.user.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserNicknameResponse {
    private Long userId;
    private String nickname;
}
