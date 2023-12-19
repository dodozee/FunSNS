package com.funs.feedservice.domain.feed.repository;

import com.funs.feedservice.domain.comment.dto.ForListInfoResponseDto;
import com.funs.feedservice.domain.feed.entity.Feed;
import com.funs.feedservice.domain.feed.entity.FeedLike;
import com.funs.feedservice.domain.feed.entity.FeedLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedLikeRepository extends JpaRepository<FeedLike, Long>, FeedLikeRepositoryCustom {
    void deleteByFeedLikeUserAndFeed(FeedLikeUser feedLikeUser, Feed feed);

//    @Query("select fl from FeedLike fl where fl.feedLikeUser.userId = :userId and fl.feed.id = :feedId")
    Boolean existsFeedLikeByFeedIdAndFeedLikeUserId(Long feedId, Long userId);

    @Query("select count(fl) from FeedLike fl where fl.feed.id = :feedId and fl.feedLikeUser.userId = :userId")
    Long countLikesByUserIdAndFeedId(Long userId, Long feedId);

    @Query("select fl from FeedLike fl where fl.feed.id = :feedId and fl.feedLikeUser.userId = :userId")
    Optional<FeedLike> findByFeedIdAndFeedLikeUserId(Long feedId, Long userId);
}
