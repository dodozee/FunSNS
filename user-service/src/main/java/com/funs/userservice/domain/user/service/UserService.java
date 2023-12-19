package com.funs.userservice.domain.user.service;

import com.funs.userservice.domain.user.dto.UserDto;
import com.funs.userservice.domain.user.dto.UserProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDto findUserByUserId(Long fromUserId, Long toUserId);
    List<UserDto> findUserByUserIds(List<Long> userIds);

    void checkDuplicateUserNickname(String teamName);

//    void addUserProfile(Long userId, String nickname, String address) throws Exception;
    void signUpUserProfile(Long userId, String nickname, String introduction, MultipartFile image, String userType) throws Exception;
    void updateUserProfile(Long userId, String nickname, String introduction, MultipartFile image) throws Exception;

//    UserRecordResponse findUserRecordByUserId(Long userId);

    List<UserDto> findUsersByNickname(String nickname);

    void deleteUser(Long aLong);

    UserDto findUserByNickname(String userNickname);

    UserDto findUserNicknameByUserId(Long userId);

    UserProfileDto findUserProfileByUserId(Long userId);

    void registerMetamaskAddress(String nickname, String metamaskAddress);

    void checkNickname(String nickname);

    List<Long> findAllUserIds();
}
