package com.funs.feedservice.domain.comment.repository;

import com.funs.feedservice.domain.comment.entity.Comment;
import com.funs.feedservice.domain.comment.entity.CommentLike;
import com.funs.feedservice.domain.comment.entity.CommentLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    @Query("select count(cl) from CommentLike cl where cl.comment.id = :commentId and cl.commentLikeUser.userId = :userId")
    Long countLikesByUserIdAndCommentId(Long userId, Long commentId);

    void deleteByCommentLikeUserAndCommentId(CommentLikeUser commentLikeUser, Long commentId);

    List<CommentLike> findAllByComment(Comment comment);

//    @Query("select count(cl) from CommentLike cl where cl.comment.id = :commentId and cl.commentLikeUser.id = :userId")
    Boolean existsByCommentLikeUserIdAndCommentId(Long userId, Long commentId);

    @Query("select cl from CommentLike cl where cl.commentLikeUser.userId = :userId and cl.comment.id = :commentId")
    Optional<CommentLike> findByUserIdAndCommentId(Long userId, Long commentId);
}
