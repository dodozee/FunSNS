package com.funs.feedservice.domain.comment.entity;

import com.funs.feedservice.domain.feed.entity.FeedLike;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "comment_like_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeUser {

    @Id @GeneratedValue
    @Column(name = "comment_like_user_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String nickname;

    @OneToMany(mappedBy="commentLikeUser", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikes = new ArrayList<>();

    public static CommentLikeUser createCommentLikeUser(Long userId, String nickname){
            CommentLikeUser commentLikeUser = new CommentLikeUser();
            commentLikeUser.userId = userId;
            commentLikeUser.nickname = nickname;
            return commentLikeUser;
    }

    //연관 관계 메서드
    public void addCommentLike(CommentLike commentLike){
        this.commentLikes.add(commentLike);
        commentLike.setCommentLikeUser(this);
    }


    public CommentLikeUser(Long userId, String nickname){
        this.userId = userId;
        this.nickname = nickname;
    }


}
