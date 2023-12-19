package com.funs.notificationservice.global.client.user;

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
