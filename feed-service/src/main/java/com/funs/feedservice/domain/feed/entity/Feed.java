package com.funs.feedservice.domain.feed.entity;

import com.funs.feedservice.domain.comment.entity.Comment;
import com.funs.feedservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "feed", indexes = {@Index(name = "idx_feed_user_id", columnList = "user_id, created_date DESC")})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "feed_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String nickname;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", length = 2500)
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "view_count")
    private Long viewCount;

    @OneToMany(mappedBy="feed", cascade = CascadeType.ALL)
    private List<FeedLike> feedLikes = new ArrayList<>();

    @OneToMany(mappedBy="feed", cascade = CascadeType.ALL)
    private List<Comment> commentLists = new ArrayList<>();

    //연관 관계 메서드
    public void addFeedLike(FeedLike feedLike){
        this.feedLikes.add(feedLike);
        feedLike.setFeed(this);
    }

    public void addComment(Comment comment){
        this.commentLists.add(comment);
        comment.setFeed(this);
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    @Builder
    public Feed(Long userId, String nickname, String title, String content, String imageUrl){
        this.userId = userId;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.viewCount = 0L;
    }

    public void update(String title, String content, String imageUrl){
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }
    public void updateViewCount(){
        this.viewCount += 1;
    }

    public void updateFeed(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public void deleteFeedLike(FeedLikeUser feedLikeUser) {
        this.feedLikes.removeIf(feedLike -> feedLike.getFeedLikeUser().equals(feedLikeUser));
    }

    public void increaseViewCount() {
        this.viewCount += 1;
    }
}
