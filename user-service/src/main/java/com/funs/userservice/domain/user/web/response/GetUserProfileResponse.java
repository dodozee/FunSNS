package com.funs.userservice.domain.user.web.response;

import com.funs.userservice.domain.user.dto.UserProfileDto;
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

    public GetUserProfileResponse(UserProfileDto userProfileDto) {
        this.userId = userProfileDto.getUserId();
        this.nickname = userProfileDto.getNickname();
        this.profileImgURL = userProfileDto.getProfileImgURL();
    }
}
