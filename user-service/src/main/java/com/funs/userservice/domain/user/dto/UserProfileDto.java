package com.funs.userservice.domain.user.dto;

import com.funs.userservice.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    private Long userId;
    private String nickname;
    private String profileImgURL;

    public UserProfileDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.profileImgURL = user.getProfileImage();
    }
}
