package com.funs.feedservice.domain.comment.entity;

import com.funs.feedservice.domain.feed.entity.Feed;
import com.funs.feedservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @Column(name = "user_id")
    private Long userId;

    private String nickname;

    @Column(length = 1000)
    private String content;

    public static Comment createComment(Long userId, String nickname,String content) {
        Comment comment = new Comment();
        comment.userId = userId;
        comment.nickname = nickname;
        comment.content = content;
        return comment;
    }

    public static Comment createReply(Long userId, String nickname, Feed feed, Comment parent, String content){
        Comment comment = new Comment();
        comment.userId = userId;
        comment.nickname = nickname;
        comment.feed = feed;
        comment.parent = parent;
        comment.content = content;

        return comment;
    }

    public void addCommentLike(CommentLike commentLike){
        this.commentLikes.add(commentLike);
        commentLike.setComment(this);
    }

    public void setFeed(Feed feed){
        this.feed = feed;
    }

    public void setParent(Comment parent){
        this.parent = parent;
    }
    public void addChildren(Comment comment){
        this.children.add(comment);
        comment.setParent(this);
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
