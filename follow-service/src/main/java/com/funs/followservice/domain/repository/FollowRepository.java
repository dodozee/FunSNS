package com.funs.followservice.domain.repository;

import com.funs.followservice.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {
    @Query("select f from Follow f where f.fromUserId = :fromUserId and f.toUserId = :toUserId")
    Optional<Follow> findByFromUserIdAndToUserId(@Param("fromUserId") Long fromUserId, @Param("toUserId")Long toUserId);

    @Query("select count(f) from Follow f where f.toUserId = :toUserId")
    Long countByToUserId(Long toUserId);

    @Query("select count(f) from Follow f where f.fromUserId = :fromUserId")
    Long countByFromUserId(Long fromUserId);

    @Query("select f.toUserId from Follow f where f.fromUserId = :userId")
    List<Long> findByFromUserId(Long userId);
}
