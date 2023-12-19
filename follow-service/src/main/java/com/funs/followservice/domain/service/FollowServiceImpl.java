package com.funs.followservice.domain.service;

import com.funs.followservice.domain.dto.FollowDto;
import com.funs.followservice.domain.dto.FollowersResultDto;
import com.funs.followservice.domain.dto.FollowingsResultDto;
import com.funs.followservice.domain.dto.ProfileFollowDto;
import com.funs.followservice.domain.entity.Follow;
import com.funs.followservice.domain.exception.AlreadyFollowException;
import com.funs.followservice.domain.exception.NotExistFollowException;
import com.funs.followservice.domain.repository.FollowRepository;
import com.funs.followservice.domain.web.response.GetFollowingListResponse;
import com.funs.followservice.global.client.user.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final FollowProducer followProducer;
    private final UserClient userClient;


    @Transactional
    @Override
    public void follow(FollowDto followDto) {
        //만약 이미 팔로우 중이라면 예외 발생
        Optional<Follow> follow = followRepository.findByFromUserIdAndToUserId(followDto.getFromUserId(), followDto.getToUserId());
        if(follow.isPresent()){
            throw new AlreadyFollowException("이미 팔로우 중입니다.");
        }
        String fromUserNickname = userClient.getNicknameUserById(followDto.getFromUserId().toString()).getData().getNickname();
        String toUserNickname = userClient.getNicknameUserById(followDto.getToUserId().toString()).getData().getNickname();
        Follow newFollow = Follow.createFollow(followDto.getFromUserId(),fromUserNickname, followDto.getToUserId(),toUserNickname);
        followRepository.save(newFollow);

        try {
            followProducer.follow(followDto.getFromUserId(),fromUserNickname, followDto.getToUserId(), toUserNickname);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    @Override
    public void unfollow(FollowDto followDto) {
        Follow follow = followRepository.findByFromUserIdAndToUserId(followDto.getFromUserId(), followDto.getToUserId()).orElseThrow(()-> new NotExistFollowException("존재하지 않는 팔로우 입니다."));
        followRepository.delete(follow);
    }

    @Override
    public ProfileFollowDto getFollowerCount(Long userId) {
        Long followerCount = followRepository.countByToUserId(userId);
        Long followingCount = followRepository.countByFromUserId(userId);

        return ProfileFollowDto.builder()
                .userId(userId)
                .followerCount(followerCount)
                .followingCount(followingCount)
                .build();
    }

    /*
    * 팔로우 가능 여부 확인
    * fromUser가 toUser에게 팔로우 하고 있으면 -> false
    * fromUser가 toUser에게 팔로우 하고 있지 않으면 -> true
     */
    @Override
    public boolean getAvailableFollow(Long fromUserId, Long toUserId) {
        Optional<Follow> follow = followRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);
        return follow.isEmpty();
    }

    @Override
    public GetFollowingListResponse getUserFollowListByUserId(Long userId) {
        List<Long> followings =followRepository.findByFromUserId(userId);
        return GetFollowingListResponse.builder()
                .followingList(followings)
                .build();
    }

    @Override
    public boolean isFollowed(Long userId, Long feedUserId) {
        Optional<Follow> follow = followRepository.findByFromUserIdAndToUserId(userId, feedUserId);
        return follow.isPresent();

    }
    @Transactional
    @Override
    public List<FollowersResultDto> getFollowers(Long userId, Long searchUserId, Long cursorId, Long size) {
        Follow cursorFollow = null;

        if(cursorId != null && cursorId != 0){
            if(!followRepository.existsById(cursorId)){
                throw new NotExistFollowException("해당 팔로우가 존재하지 않습니다.");
            }
            if(!Objects.equals(followRepository.getReferenceById(cursorId).getToUserId(), searchUserId)){
                throw new NotExistFollowException("해당 팔로우가 존재하지 않습니다.");
            }
            cursorFollow = followRepository.findById(cursorId).orElseThrow(() -> new NotExistFollowException("해당 팔로우가 존재하지 않습니다."));
        }

        List<FollowersResultDto> followersResultDtos = followRepository.findFollowersByUserId(searchUserId, cursorFollow, size);
        for(FollowersResultDto followersResultDto : followersResultDtos){
            followersResultDto.setProfileImage(userClient.getUserProfileById(String.valueOf(followersResultDto.getUserId())).getData().getProfileImgURL());
            followersResultDto.setFollowed(isFollowed(userId, followersResultDto.getUserId()));
        }

        return followersResultDtos;
    }

    @Transactional
    @Override
    public List<FollowingsResultDto> getFollowings(Long userId, Long searchUserId, Long cursorId, Long size) {
        Follow cursorFollow = null;

        if(cursorId != null && cursorId != 0){
            if(!followRepository.existsById(cursorId)){
                throw new NotExistFollowException("해당 팔로우가 존재하지 않습니다.");
            }
            if(!Objects.equals(followRepository.getReferenceById(cursorId).getFromUserId(), searchUserId)){
                throw new NotExistFollowException("해당 팔로우가 존재하지 않습니다.");
            }
            cursorFollow = followRepository.findById(cursorId).orElseThrow(() -> new NotExistFollowException("해당 팔로우가 존재하지 않습니다."));
        }
        List<FollowingsResultDto> followingsResultDtos = followRepository.findFollowingsByUserId(searchUserId, cursorFollow, size);
        for(FollowingsResultDto followingsResultDto : followingsResultDtos){
            followingsResultDto.setProfileImage(userClient.getUserProfileById(String.valueOf(followingsResultDto.getUserId())).getData().getProfileImgURL());
            followingsResultDto.setFollowed(isFollowed(userId, followingsResultDto.getUserId()));
        }

        return followingsResultDtos;
    }




}
