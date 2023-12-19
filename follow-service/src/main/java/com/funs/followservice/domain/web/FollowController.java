package com.funs.followservice.domain.web;

import com.funs.followservice.domain.dto.FollowDto;
import com.funs.followservice.domain.dto.FollowersResultDto;
import com.funs.followservice.domain.dto.FollowingsResultDto;
import com.funs.followservice.domain.dto.ProfileFollowDto;
import com.funs.followservice.domain.service.FollowService;
import com.funs.followservice.domain.web.response.GetFollowedResponse;
import com.funs.followservice.domain.web.response.GetFollowingListResponse;
import com.funs.followservice.domain.web.response.ProfileFollowResponse;
import com.funs.followservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    //TODO 팔로우 요청 -> 팔로우 하는 상대방에게 알림 전송, 팔로우 하는 순간 뉴스피드에 해당 유저의 게시글이 보여야 함
    @PostMapping("/follow/{toUserId}")
    ResponseEntity<Result> follow(@RequestHeader("user-id") String fromUserId,
                                  @PathVariable("toUserId") String toUserId){
        System.out.println("팔로우 요청 api");
        FollowDto followDto = FollowDto.builder()
                .fromUserId(Long.valueOf(fromUserId))
                .toUserId(Long.valueOf(toUserId))
                .build();
        followService.follow(followDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("팔로우 요청이 완료되었습니다."));
    }

    //TODO 팔로우 취소 요청
    @DeleteMapping("/unfollow/{toUserId}")
    ResponseEntity<Result> unfollow(@RequestHeader("user-id") String fromUserId,
                                    @PathVariable("toUserId") String toUserId){
        System.out.println("팔로우 취소 요청 api");
        FollowDto followDto = FollowDto.builder()
                .fromUserId(Long.valueOf(fromUserId))
                .toUserId(Long.valueOf(toUserId))
                .build();
        followService.unfollow(followDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("팔로우 취소 요청이 완료되었습니다."));
    }

    @GetMapping("/feign/profile/follow/count/{userId}")
    ResponseEntity<Result> getFollowerCount(@PathVariable("userId") String userId){
        System.out.println("팔로워 수 조회 요청 api");
        ProfileFollowDto profileFollowDto = followService.getFollowerCount(Long.valueOf(userId));

        ProfileFollowResponse response = profileFollowDto.toResponse();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }


    //TODO 팔로워 리스트 조회 요청
    @GetMapping("/followers/{searchUserId}")
    ResponseEntity<Result> getFollowers(@RequestHeader("user-id") String userId,
                                        @PathVariable("searchUserId") String searchUserId,
                                        @RequestParam("cursor") Long cursorId,
                                        @RequestParam("size") Long size){
        System.out.println("팔로워 리스트 조회 요청 api");
        List<FollowersResultDto> followersResultDtos = followService.getFollowers(Long.valueOf(userId), Long.valueOf(searchUserId), cursorId, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(followersResultDtos,"팔로워 리스트 조회 요청이 완료되었습니다."));
    }

    //TODO 팔로잉 리스트 조회 요청
    @GetMapping("/followings/{searchUserId}")
    ResponseEntity<Result> getFollowings(@RequestHeader("user-id") String userId,
                                         @PathVariable("searchUserId") String searchUserId,
                                         @RequestParam("cursor") Long cursorId,
                                         @RequestParam("size") Long size){
        System.out.println("팔로잉 리스트 조회 요청 api");

        List<FollowingsResultDto> followingsResultDtos = followService.getFollowings(Long.valueOf(userId), Long.valueOf(searchUserId), cursorId, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(followingsResultDtos,"팔로잉 리스트 조회 요청이 완료되었습니다."));
    }

    @GetMapping("/feign/available/follow/{fromUserId}/{toUserId}")
    boolean getAvailableFollow(@PathVariable("fromUserId") String fromUserId,
                               @PathVariable("toUserId") String toUserId){
        System.out.println("팔로우 가능 여부 조회 요청 api");
        return followService.getAvailableFollow(Long.valueOf(fromUserId), Long.valueOf(toUserId));
    }

    @GetMapping("/feign/following-list/{userId}")
    ResponseEntity<Result> getUserFollowListByUserId(@PathVariable("userId") String userId){
        System.out.println("팔로잉 리스트 조회 요청 api");
        GetFollowingListResponse response = followService.getUserFollowListByUserId(Long.valueOf(userId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }

    @GetMapping("/feign/followed/user/{userId}/feed/{feedUserId}")
    ResponseEntity<Result> isFollowed(@PathVariable("userId") String userId,
                                      @PathVariable("feedUserId") String feedUserId){
        System.out.println("팔로우 여부 조회 요청 api");
        boolean isFollowed = followService.isFollowed(Long.valueOf(userId), Long.valueOf(feedUserId));
        GetFollowedResponse response = GetFollowedResponse.builder()
                .isFollowed(isFollowed)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }



}
