package com.funs.userservice.domain.user.web.response;


import com.funs.userservice.domain.user.dto.UserProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserFeignResponse {
    private Long userId;
    private String nickname;
    private String profileImage;

    public GetUserFeignResponse(UserProfileDto userDto) {
        this.userId = userDto.getUserId();
        this.nickname = userDto.getNickname();
        this.profileImage = userDto.getProfileImgURL();
    }
}
