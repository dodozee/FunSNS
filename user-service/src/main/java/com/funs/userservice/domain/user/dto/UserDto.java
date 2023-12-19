package com.funs.userservice.domain.user.dto;

import com.funs.userservice.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String introduction;
    private String profileImage;
    private String dtype;
    private Long followerCount;
    private Long followingCount;
    private boolean availableFollow;
    private String userType;



    //생성 메소드
    public UserDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.introduction = user.getIntroduction();
        this.nickname = user.getNickname();
        this.dtype = user.getDtype();
        if(user.getProfileImage()==null){
            profileImage="https://with-sports-s3.s3.ap-northeast-2.amazonaws.com/static/bae53540-5cd1-4457-a42d-03449ca7a0a0basic.jpeg";
        }else {
            this.profileImage = user.getProfileImage();
        }
    }

    public UserDto(User user, String profileImage){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.introduction = user.getIntroduction();
        this.profileImage = profileImage;
        this.dtype = user.getDtype();
    }
    public UserDto(User user, Long followerCount, Long followingCount, boolean availableFollow){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.introduction = user.getIntroduction();
        this.nickname = user.getNickname();
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.availableFollow = availableFollow;
        this.dtype = user.getDtype();
        if(user.getProfileImage()==null){
            profileImage="https://with-sports-s3.s3.ap-northeast-2.amazonaws.com/static/bae53540-5cd1-4457-a42d-03449ca7a0a0basic.jpeg";
        }else {
            this.profileImage = user.getProfileImage();
        }
    }


    @Builder
    public UserDto(Long id, String email, String password, String name, String nickname, String dtype, String introduction, String userType){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.introduction = introduction;
        this.nickname = nickname;
        this.dtype = dtype;
        this.userType = userType;
    }
}
