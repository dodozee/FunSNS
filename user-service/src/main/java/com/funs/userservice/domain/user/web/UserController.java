package com.funs.userservice.domain.user.web;


import com.funs.userservice.domain.user.dto.UserDto;
import com.funs.userservice.domain.user.dto.UserProfileDto;
import com.funs.userservice.domain.user.service.UserService;
import com.funs.userservice.domain.user.web.request.CreateFormMetamaskRequest;
import com.funs.userservice.domain.user.web.request.SignUpUserProfileRequest;
import com.funs.userservice.domain.user.web.request.UpdateUserProfileRequest;
import com.funs.userservice.domain.user.web.response.*;
import com.funs.userservice.global.dto.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


//    @GetMapping("/user")
//    public ResponseEntity getUserByToken(@Valid @RequestHeader(value="user-id") String userId){
//
//        UserDto userDto = userService.findUserByUserId(Long.parseLong(userId));
//
//
//        GetUserByTokenResponse getUserByTokenResponse = new GetUserByTokenResponse(userDto);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(Result.createSuccessResult(getUserByTokenResponse));
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    static class GetUserByTokenResponse{
//        private Long userId;
//        private String email;
//        private String name;
//        private String nickname;
//        private String address;
//
//        public GetUserByTokenResponse(UserDto userDto){
//            this.userId = userDto.getId();
//            this.email = userDto.getEmail();
//            this.name = userDto.getName();
//            this.nickname = userDto.getNickname();
//
//        }
//    }

    @GetMapping("/user/nickname/feign/{userNickname}")
    public ResponseEntity<Result> getUserByUserNickname(@PathVariable("userNickname") String userNickname){
        System.out.println("유저Id 조회 api");
        UserDto userDto = userService.findUserByNickname(userNickname);

        GetUserResponse getUserResponse = new GetUserResponse(userDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(getUserResponse));
    }

    //TODO 유저Id로 프로필 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<Result> getUserUserById(@RequestHeader("user-id") String fromUserId,
                                                  @PathVariable("userId") String toUserId){

        System.out.println("기본정보 조회 api");

        UserDto userDto = userService.findUserByUserId(Long.valueOf(fromUserId), Long.valueOf(toUserId));


        GetUserResponse getUserResponse = new GetUserResponse(userDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(getUserResponse));
    }

    @GetMapping("/feign/user/{userId}")
    ResponseEntity<Result> getUserUserByIdFeign(@PathVariable("userId") String userId){

        System.out.println("닉네임 조회 api");

        UserDto userDto = userService.findUserNicknameByUserId(Long.valueOf(userId));
        GetUserNicknameResponse getUserNicknameResponse = new GetUserNicknameResponse(userDto.getId(), userDto.getNickname());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(getUserNicknameResponse));

//    @GetMapping("/user/other/profile/{userId}")
//    public ResponseEntity<Result> getOtherUserProfile(@PathVariable("userId") String userId){
//        UserDto userDto = userService.findUserByUserId(Long.valueOf(userId));
//        UserRecordResponse userRecordResponse = userService.findUserRecordByUserId(Long.valueOf(userId));
//        if(userRecordResponse==null){
//            UserProfileResponse userProfileResponse = new UserProfileResponse(userDto);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(Result.createSuccessResult(userProfileResponse,"아직 팀 기록이 없습니다."));
//        }else {
//            UserProfileResponse userProfileResponse = new UserProfileResponse(userDto, userRecordResponse);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(Result.createSuccessResult(userProfileResponse));
//        }


    }








    //TODO 닉네임 중복 검사
    @GetMapping("/signup/check/nickname/")
    public ResponseEntity checkNickname(HttpServletRequest request){
        String nickname = request.getParameter("nickname");
        userService.checkDuplicateUserNickname(nickname);
        System.out.println("중복되는 닉네임이 없습니다.");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("중복되는 닉네임이 없습니다.",nickname));
    }

//    //TODO 회원정보 추가 정보 받기
//    @PutMapping("/signup/profile")
//    public ResponseEntity addProfile(@Valid @RequestHeader(value="user-id") String userId,
//                                     @RequestBody AddUserProfileRequest addProfileRequest) throws Exception {
//        String nickname = addProfileRequest.getNickname();
//        String area = addProfileRequest.getArea();
//        userService.addUserProfile(Long.valueOf(userId), nickname, area);
//        System.out.println("회원정보 추가 정보를 저장했습니다.");
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(Result.createSuccessResult("회원정보 추가 정보를 저장했습니다."));
//    }

    //TODO 사용자 프로필 조회(닉네임, 소개, 지역, 이미지, 티어, 승률 등)
//    @GetMapping("/user/profile")
//    public ResponseEntity<Result> getUserProfile(@Valid @RequestHeader(value="user-id") String userId){
//        UserDto userDto = userService.findUserByUserId(Long.valueOf(userId));
//        UserRecordResponse userRecordResponse = userService.findUserRecordByUserId(Long.valueOf(userId));
//        if(userRecordResponse==null){
//            UserProfileResponse userProfileResponse = new UserProfileResponse(userDto);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(Result.createSuccessResult(userProfileResponse,"아직 팀 기록이 없습니다."));
//        }else {
//            UserProfileResponse userProfileResponse = new UserProfileResponse(userDto, userRecordResponse);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(Result.createSuccessResult(userProfileResponse));
//        }
//
//
//    }

//    TODO 사용자 프로필 최초 등록 api(닉네임, 소개, 프로필 이미지, 역할)
    @PutMapping("/user/profile")
    public ResponseEntity signUpUserProfile(@Valid @RequestHeader(value="user-id") String userId,
                                            @RequestPart(value = "updateProfile") SignUpUserProfileRequest signUpUserProfileRequest,
                                            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

        System.out.println("사용자 프로필 최초 등록 및 수정 api");
        if(image != null){
            System.out.println("사용자 프로필 수정 api image가 null이 아님");
        }else if(image == null){
            System.out.println("사용자 프로필 수정 api image가 null임");
        }
        String nickname = signUpUserProfileRequest.getNickname();
        String introduction = signUpUserProfileRequest.getIntroduction();
        String userType = signUpUserProfileRequest.getUserType();
        userService.signUpUserProfile(Long.valueOf(userId), nickname, introduction, image, userType);
        System.out.println("회원정보 추가 정보를 수정했습니다.");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("회원정보 추가 정보를 수정했습니다."));
    }

    //TODO 사용자 프로필 수정 api(닉네임, 소개, 프로필 이미지)
    @PutMapping("/user/update/profile")
    public ResponseEntity updateUserProfile(@Valid @RequestHeader(value="user-id") String userId,
                                            @RequestPart(value = "updateProfile") UpdateUserProfileRequest updateUserProfileRequest,
                                            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

        System.out.println("사용자 수정 api");
        if(image != null){
            System.out.println("사용자 프로필 수정 api image가 null이 아님");
        }else if(image == null){
            System.out.println("사용자 프로필 수정 api image가 null임");
        }
        String nickname = updateUserProfileRequest.getNickname();
        String introduction = updateUserProfileRequest.getIntroduction();
        userService.updateUserProfile(Long.valueOf(userId), nickname, introduction, image);
        System.out.println("회원정보 추가 정보를 수정했습니다.");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("회원정보 추가 정보를 수정했습니다."));
    }

    //TODO 사용자 닉네임 검색
    @GetMapping("/user/nickname/{nickname}")
    public ResponseEntity<Result> searchUserByNickname(@PathVariable("nickname") String nickname){

        List<UserDto> userDto = userService.findUsersByNickname(nickname);
        List<GetUserResponse> userResponse;
        if(userDto.size() == 0){
            userResponse = null;
        }else{
            userResponse= userDto
                    .stream()
                    .map(GetUserResponse::new)
                    .toList();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(userResponse));
    }

    //TODO 회원 탈퇴
    @DeleteMapping("/user")
    ResponseEntity<Result> deleteUser(@Valid @RequestHeader(value="user-id") String userId){
        userService.deleteUser(Long.valueOf(userId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("회원 탈퇴가 완료되었습니다."));
    }


    @GetMapping("/feign/user-profile/{userId}")
    Result<GetUserProfileResponse> getUserProfileById(@PathVariable("userId") String userId){
        System.out.println("유저 프로필 조회 api");
        UserProfileDto userProfileDto = userService.findUserProfileByUserId(Long.valueOf(userId));
        GetUserProfileResponse getUserProfileResponse = new GetUserProfileResponse(userProfileDto);
        return Result.createSuccessResult(getUserProfileResponse);

    }

    @GetMapping("/feign/user/nickname/{userId}")
    Result<GetUserFeignResponse> getUserById(@PathVariable("userId") String userId){
        System.out.println("유저 프로필 조회 api");
        UserProfileDto userDto = userService.findUserProfileByUserId(Long.valueOf(userId));
        GetUserFeignResponse getUserResponse = new GetUserFeignResponse(userDto);
        return Result.createSuccessResult(getUserResponse);

    }

    //TODO 닉네임 존재 여부 확인
    @GetMapping("/metamask/{nickname}")
    ResponseEntity<Result> getUserByMetamaskAddress(@PathVariable("nickname") String nickname){
        System.out.println("메타마스크 주소로 유저 조회 api");
        userService.checkNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("닉네임이 존재합니다."));
    }

    //TODO 메타마스크 주소 등록
    @PostMapping("/metamask/address")
    ResponseEntity<Result> getUserByMetamaskAddress(@RequestBody CreateFormMetamaskRequest createFormMetamaskRequest){
        System.out.println("메타마스크 주소로 유저 조회 api");
        String nickname = createFormMetamaskRequest.getNickname();
        String metamaskAddress = createFormMetamaskRequest.getMetamaskAddress();
        userService.registerMetamaskAddress(nickname,metamaskAddress);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("메타마스크 주소 등록이 완료되었습니다."));
    }

    @GetMapping("/feign/admin/all-user-ids")
    ResponseEntity<Result> getAllUserIds(){
        System.out.println("모든 유저 아이디 조회 api");
        List<Long> userIds = userService.findAllUserIds();
        GetAllUserIdsResponse getAllUserIdsResponse = new GetAllUserIdsResponse(userIds);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(getAllUserIdsResponse));
    }



}
