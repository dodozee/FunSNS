package com.funs.feedservice.domain.feed.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "feed_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedLike {

    @Id @GeneratedValue
    @Column(name = "feed_like_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_like_user_id")
    private FeedLikeUser feedLikeUser;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="feed_id")
    private Feed feed;

    public void setFeed(Feed feed){
        this.feed = feed;
    }

    public void setFeedLikeUser(FeedLikeUser feedLikeUser){
        this.feedLikeUser = feedLikeUser;
    }
    @Builder
    public FeedLike(FeedLikeUser feedLikeUser, Feed feed){
        this.feedLikeUser = feedLikeUser;
        this.feed = feed;
    }
}
