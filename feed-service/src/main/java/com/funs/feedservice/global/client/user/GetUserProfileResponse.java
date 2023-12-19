package com.funs.feedservice.global.client.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserProfileResponse {
    private Long userId;
    private String nickname;
    private String profileImgURL;
}
