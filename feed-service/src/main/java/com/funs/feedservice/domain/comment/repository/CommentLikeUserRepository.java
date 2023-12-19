package com.funs.feedservice.domain.comment.repository;

import com.funs.feedservice.domain.comment.entity.CommentLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeUserRepository extends JpaRepository<CommentLikeUser, Long> {

    Optional<CommentLikeUser> findByUserId(Long userId);
}
