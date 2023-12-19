package com.funs.feedservice.domain.feed.repository;

import com.funs.feedservice.domain.feed.entity.FeedLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedLikeUserRepository extends JpaRepository<FeedLikeUser, Long> {
    Optional<FeedLikeUser> findByUserId(Long userId);


//    @Query("select flu from FeedLikeUser flu where fl.userId = :userId and flu. :feedId")
//    Optional<FeedLikeUser> findByUserIdAndFeedLikeId(Long userId, Long feedId);
}
