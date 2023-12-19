package com.funs.feedservice.domain.feed.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "feed_like_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedLikeUser {

    @Id @GeneratedValue
    @Column(name = "feed_like_user_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String nickname;

    @OneToMany(mappedBy="feedLikeUser", cascade = CascadeType.ALL)
    private List<FeedLike> feedLikes = new ArrayList<>();

    //연관 관계 메서드
    public void addFeedLike(FeedLike feedLike){
        this.feedLikes.add(feedLike);
        feedLike.setFeedLikeUser(this);
    }
    @Builder
    public FeedLikeUser(Long userId, String nickname){
        this.userId = userId;
        this.nickname = nickname;
    }
}
