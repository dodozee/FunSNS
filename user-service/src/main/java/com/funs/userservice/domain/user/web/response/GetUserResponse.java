package com.funs.userservice.domain.user.web.response;

import com.funs.userservice.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
    private Long userId; // 유저Id
    private String name; // 이름
    private String nickname; // 닉네임
    private String profileImage; // 프로필 이미지
    private String introduction; // 자기소개
    private boolean availableFollow; // 팔로우 가능 여부 TODO: 팔로우 가능 여부를 판단하는 로직 필요
    private Long followerCount; // 팔로워 수
    private Long followingCount; // 팔로잉 수
    private String userType; // 사용자 타입

    public GetUserResponse(UserDto userDto){
        this.userId = userDto.getId();
        this.name = userDto.getName();
        this.nickname = userDto.getNickname();
        this.profileImage = userDto.getProfileImage();
        this.availableFollow = userDto.isAvailableFollow();
        this.introduction = userDto.getIntroduction();
        this.followerCount = userDto.getFollowerCount();
        this.followingCount = userDto.getFollowingCount();
        this.userType = userDto.getUserType();
    }
}