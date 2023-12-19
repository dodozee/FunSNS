package com.funs.feedservice.domain.feed.service;

import com.funs.feedservice.domain.comment.dto.ForListInfoResponseDto;
import com.funs.feedservice.domain.comment.entity.CommentLikeUser;
import com.funs.feedservice.domain.comment.exception.NotExistCommentException;
import com.funs.feedservice.domain.feed.dto.DetailFeedResponseDto;
import com.funs.feedservice.domain.feed.dto.FeedResultDto;
import com.funs.feedservice.domain.feed.entity.Feed;
import com.funs.feedservice.domain.feed.entity.FeedLike;
import com.funs.feedservice.domain.feed.entity.FeedLikeUser;
import com.funs.feedservice.domain.feed.exception.AlreadyLikedException;
import com.funs.feedservice.domain.feed.exception.NotExistFeedException;
import com.funs.feedservice.domain.feed.repository.FeedLikeRepository;
import com.funs.feedservice.domain.feed.repository.FeedLikeUserRepository;
import com.funs.feedservice.domain.feed.repository.FeedRepository;
import com.funs.feedservice.domain.feed.web.request.CreateFeedRequest;
import com.funs.feedservice.global.client.follow.FollowClient;
import com.funs.feedservice.global.client.user.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService{

    private final FeedRepository feedRepository;
    private final FeedLikeUserRepository feedLikeUserRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final UserClient userClient;
    private final FollowClient followClient;
    private final FeedProducer feedProducer;
    private final S3Uploader s3Uploader;

    @Transactional
    @Override
    public void createFeed(Long userId, CreateFeedRequest content, MultipartFile image) throws IOException {
        String nickname = userClient.getNicknameUserById(String.valueOf(userId)).getData().getNickname();
        if (!image.isEmpty()) {
            String imageUrl = s3Uploader.upload(userId, image, "static");
            saveFeed(userId, nickname, content, imageUrl);
        } else {
            saveFeed(userId, nickname, content, null);
        }
        try {
            feedProducer.feed(userId, content.getTitle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    @Override
    public void modifyFeed(Long userId, Long feedId, CreateFeedRequest content, MultipartFile image) throws IOException {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));
        if (!image.isEmpty()) {
            String imageUrl = s3Uploader.upload(userId, image, "static");
            feed.updateFeed(content.getTitle(), content.getContent(), imageUrl);
        } else {
            feed.updateFeed(content.getTitle(),content.getContent(), null);
        }

    }

    @Transactional
    @Override
    public void deleteFeed(Long userId, Long feedId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));
        feedRepository.delete(feed);
    }

    @Transactional
    @Override
    public void likeFeed(Long userId, Long feedId) {
        String nickname = userClient.getNicknameUserById(String.valueOf(userId)).getData().getNickname();
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));

//        FeedLikeUser feedLikeUser = feedLikeUserRepository.findByUserId(userId).orElseThrow(() -> new NotExistCommentException("좋아요 누른 사용자가 존재하지 않습니다."));


        if(feedLikeRepository.findByFeedIdAndFeedLikeUserId(userId, feedId).isPresent()){
            throw new AlreadyLikedException("이미 좋아요를 누른 피드입니다.");
        }

        FeedLikeUser newFeedLikeUser = FeedLikeUser.builder()
                .userId(userId)
                .nickname(nickname)
                .build();

        feedLikeUserRepository.save(newFeedLikeUser);


        FeedLike feedLike = FeedLike.builder()
                .feed(feed)
                .feedLikeUser(newFeedLikeUser)
                .build();

        feed.addFeedLike(feedLike);

    }

    @Transactional
    @Override
    public void unlikeFeed(Long userId, Long feedId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));
        FeedLike feedLike = feedLikeRepository.findByFeedIdAndFeedLikeUserId(feedId, userId).orElseThrow(() -> new NotExistFeedException("해당 좋아요가 존재하지 않습니다."));

        FeedLikeUser feedLikeUser = feedLike.getFeedLikeUser();

//        if(0==feedLikeRepository.countLikesByUserIdAndFeedId(feedId, userId)){
//            throw new NotExistFeedException("좋아요를 누르지 않았습니다.");
//        }
        feedLikeUserRepository.delete(feedLikeUser);
        feedLikeRepository.deleteByFeedLikeUserAndFeed(feedLikeUser, feed);
    }

    @Override
    public List<ForListInfoResponseDto> feedLikeList(Long userId, Long feedId, Long cursorId, Long size) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));

        if(cursorId != null && cursorId != 0){
            if(!feedLikeRepository.existsById(cursorId)){
                throw new NotExistFeedException("해당 좋아요가 존재하지 않습니다.");
            }
            if(feedLikeRepository.getReferenceById(cursorId).getFeed() != feed){
                throw new NotExistFeedException("해당 좋아요가 존재하지 않습니다.");
            }
        }
        List<ForListInfoResponseDto> forListInfoResponseDtoList = feedLikeRepository.feedLikeList(feed, cursorId, size);

        for(ForListInfoResponseDto dto : forListInfoResponseDtoList){
            String profileImgURL = userClient.getUserProfileById(String.valueOf(dto.getUserId())).getData().getProfileImgURL();
            dto.setProfileImgURL(profileImgURL);
            dto.setFollowed(followClient.isFollowed(String.valueOf(userId), String.valueOf(dto.getUserId())).getData().isFollowed());
        }
        return forListInfoResponseDtoList;
    }

    @Override
    public List<FeedResultDto> newsfeed(Long userId, Long cursorId, Long size) {
        List<Long> followingList = followClient.getUserFollowListByUserId(String.valueOf(userId)).getData().getFollowingList();
        followingList.add(userId);
        Feed cursorFeed = null;
        if (cursorId != null && cursorId != 0) {
            cursorFeed = feedRepository.findById(cursorId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));
        }

        List<FeedResultDto> result = feedRepository.searchFeedBy(userId, followingList, cursorFeed, size);

        for(FeedResultDto dto : result){
            String profileImgURL = userClient.getUserProfileById(String.valueOf(dto.getUserId())).getData().getProfileImgURL();
            dto.setProfileImgURL(profileImgURL);
            feedLikeRepository.findByFeedIdAndFeedLikeUserId(dto.getFeedId(), userId).ifPresentOrElse(
                            (feedLike) -> dto.setIsLiked(true),
                            () -> dto.setIsLiked(false)
                    );
        }
        return result;
    }

    @Override
    public List<FeedResultDto> adminNewsfeed(Long userId, Long cursorId, Long size) {
        List<Long> getUserIds = userClient.getAllUserIds().getData().getUserIds();
        getUserIds.add(userId);
        Feed cursorFeed = null;
        if (cursorId != null && cursorId != 0) {
            cursorFeed = feedRepository.findById(cursorId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));
        }

        List<FeedResultDto> result = feedRepository.searchFeedBy(userId, getUserIds, cursorFeed, size);

        for(FeedResultDto dto : result){
            String profileImgURL = userClient.getUserProfileById(String.valueOf(dto.getUserId())).getData().getProfileImgURL();
            dto.setProfileImgURL(profileImgURL);
            feedLikeRepository.findByFeedIdAndFeedLikeUserId(dto.getFeedId(), userId).ifPresentOrElse(
                    (feedLike) -> dto.setIsLiked(true),
                    () -> dto.setIsLiked(false)
            );
        }
        return result;
    }

    @Transactional
    @Override
    public DetailFeedResponseDto feedDetail(Long userId, Long feedId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));

        boolean isLiked = (feedLikeRepository.findByFeedIdAndFeedLikeUserId(feedId, userId).isPresent());
        String profileImgURL = userClient.getUserProfileById(String.valueOf(feed.getUserId())).getData().getProfileImgURL();
        DetailFeedResponseDto response = DetailFeedResponseDto.builder()
                .feedId(feedId)
                .userId(feed.getUserId())
                .nickname(feed.getNickname())
                .title(feed.getTitle())
                .content(feed.getContent())
                .feedImgURL(feed.getImageUrl())
                .likeCount(feed.getFeedLikes().size())
                .commentCount(feed.getCommentLists().size())
                .isLiked(isLiked)
                .isMine(feed.getUserId().equals(userId))
                .isFollowed(followClient.isFollowed(String.valueOf(userId), String.valueOf(feed.getUserId())).getData().isFollowed())
                .profileImgURL(profileImgURL)
                .viewCount(feed.getViewCount())
                .createdDate(feed.getCreatedAt())
                .build();

        feed.increaseViewCount();

        return response;
    }

    @Transactional
    @Override
    public List<FeedResultDto> timeline(Long userId, Long cursorId, Long size) {

        List<Long> me = List.of(userId);
        Feed cursorFeed = null;
        if (cursorId != null && cursorId != 0) {
            cursorFeed = feedRepository.findById(cursorId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));
        }

        List<FeedResultDto> result = feedRepository.searchFeedBy(userId, me, cursorFeed, size);

        for(FeedResultDto dto : result){
            String profileImgURL = userClient.getUserProfileById(String.valueOf(dto.getUserId())).getData().getProfileImgURL();
            dto.setProfileImgURL(profileImgURL);
            dto.setIsLiked(feedLikeRepository.findByFeedIdAndFeedLikeUserId(dto.getFeedId(), userId).isPresent());
        }
        return result;
    }

    private void saveFeed(Long userId, String nickname, CreateFeedRequest content, String imageUrl) {
        Feed feed = Feed.builder()
                .userId(userId)
                .nickname(nickname)
                .title(content.getTitle())
                .content(content.getContent())
                .imageUrl(imageUrl)
                .build();
        feedRepository.save(feed);
    }
}
