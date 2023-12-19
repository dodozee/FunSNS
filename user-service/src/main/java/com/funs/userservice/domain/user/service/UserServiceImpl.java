package com.funs.userservice.domain.user.service;

import com.funs.userservice.domain.user.dto.UserDto;
import com.funs.userservice.domain.user.dto.UserProfileDto;
import com.funs.userservice.domain.user.entity.User;
import com.funs.userservice.domain.user.exception.DuplicateUserNicknameException;
import com.funs.userservice.domain.user.exception.NotExistUserException;
import com.funs.userservice.domain.user.repository.UserRepository;
import com.funs.userservice.global.client.follow.FollowClient;
import com.funs.userservice.global.client.follow.ProfileFollowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService,UserDetailsService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;
    private final S3Uploader s3Uploader;
    private final FollowClient followClient;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 입니다."));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getDtype()));

        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(), authorities);


    }

    public List<UserDto> findUserByUserIds(List<Long> userIds){
        return userRepository.findAllById(userIds)
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

    }

    /*
      -toUserId가 본인이면 팔로우 안되게 함
      -fromUserId, toUserId를 보내서 팔로우 여부를 확인
      false => 팔로우 안되어 있음 => [팔로우 요청] 버튼 활성화
      true => 팔로우 되어 있음 => [팔로우 취소] 버튼 활성화

       */
    public UserDto findUserByUserId(Long fromUserId, Long toUserId){

        User user = userRepository.findById(toUserId)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));

        ProfileFollowResponse followResponse = followClient.getFollowerCount(String.valueOf(toUserId)).getData();
        if(fromUserId.equals(toUserId)){
            return new UserDto(user, followResponse.getFollowerCount(), followResponse.getFollowingCount(), false);
        }else{
            boolean availableFollow = followClient.getAvailableFollow(String.valueOf(fromUserId), String.valueOf(toUserId));
            return new UserDto(user, followResponse.getFollowerCount(), followResponse.getFollowingCount(), availableFollow);
        }

    }


    //닉네임 중복 체크

    @Override
    public void checkDuplicateUserNickname(String nickname) {

        Optional<User> user = userRepository.findByNickname(nickname);
        if(user.isPresent()){
            throw new DuplicateUserNicknameException("이미 존재하는 팀 이름입니다.");
        }
    }

    @Transactional
    @Override
    public void signUpUserProfile(Long userId, String nickname, String introduction, MultipartFile image, String userType) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));
        //image가 있을 경우만
        if(image != null){
            s3Uploader.upload(user.getId(), image, "static");
        }else{
            user.addUserBasicProfileImage();
        }
        user.updateUserProfile(nickname,introduction,userType);

        userProducer.createUser(user);


    }


//    @Transactional
//    @Override
//    public void addUserProfile(Long userId, String nickname, String address) throws Exception {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));
//
//        user.addUserProfile(nickname,address);
//        userProducer.createUser(user);
//
//
////        userRepository.save(user);
//    }

    @Transactional
    @Override
    public void updateUserProfile(Long userId, String nickname, String introduction, MultipartFile image) throws Exception {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));
            //image가 있을 경우만
            if(image != null){
                s3Uploader.upload(user.getId(), image, "static");
            }
            user.updateUserProfile(nickname,introduction, user.getUserType());
                userProducer.updateUser(user);



    }

    //Feign으로 조회
//    @Override
//    public UserRecordResponse findUserRecordByUserId(Long userId) {
//        return null;
//    }

    @Override
    public List<UserDto> findUsersByNickname(String nickname) {
        List<User> users = userRepository.findUserByNicknameContaining(nickname);
        return users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteUser(Long aLong) {
        User user = userRepository.findById(aLong)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));

        userRepository.delete(user);
    }

//    @Override
//    public UserRecordResponse findUserRecordByUserId(Long userId) {
//
//        Result<AverageUserRecordResponse> averageRecordByUserId = recordClient.getAverageRecordByUserId(String.valueOf(userId));
//        return new UserRecordResponse(averageRecordByUserId);
////        Result<GetTeamIdsResponse> teamByUserIds = teamClient.getTeamByUserId(userId);
////        if(teamByUserIds.getData().getTeamIds().size() == 0){
////            return null;
////        }
////        List<Long> teamIds = teamByUserIds.getData().getTeamIds();
////
////        List<TeamRecordResponse> teamRecordResponses = new ArrayList<>();
////        for(Long teamId : teamIds){
////            Result<TeamRecordResponse> recordByTeamId = recordClient.getRecordByTeamId(String.valueOf(teamId));
////            teamRecordResponses.add(recordByTeamId.getData());
////            }
////        return teamRecordResponses;
//    }

    @Override
    public UserDto findUserByNickname(String userNickname) {
        User user = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));
        return new UserDto(user);
    }

    @Override
    public UserDto findUserNicknameByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));
        return new UserDto(user);
    }

    @Override
    public UserProfileDto findUserProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));
        return new UserProfileDto(user);
    }

    @Transactional
    @Override
    public void registerMetamaskAddress(String nickname, String metamaskAddress) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 사용자 입니다."));
        user.registerMetamaskAddress(metamaskAddress);

    }

    @Override
    public void checkNickname(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
        if(user.isEmpty()){
            throw new NotExistUserException("존재하지 않는 사용자 입니다.");
        }
    }

    @Override
    public List<Long> findAllUserIds() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }


}
