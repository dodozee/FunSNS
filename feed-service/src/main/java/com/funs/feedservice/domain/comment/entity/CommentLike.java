package com.funs.feedservice.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @Id @GeneratedValue
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_like_user_id")
    private CommentLikeUser commentLikeUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public static CommentLike createCommentLike(CommentLikeUser commentLikeUser, Comment comment) {
        CommentLike commentLike = new CommentLike();
        commentLike.commentLikeUser = commentLikeUser;
        commentLike.comment = comment;
        return commentLike;
    }

    public void setComment(Comment comment){
        this.comment = comment;
    }

    public void setCommentLikeUser(CommentLikeUser commentLikeUser){
        this.commentLikeUser = commentLikeUser;
    }
}
