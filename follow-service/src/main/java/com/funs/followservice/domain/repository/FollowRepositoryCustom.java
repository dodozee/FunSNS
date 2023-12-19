package com.funs.followservice.domain.repository;

import com.funs.followservice.domain.dto.FollowersResultDto;
import com.funs.followservice.domain.dto.FollowingsResultDto;
import com.funs.followservice.domain.entity.Follow;

import java.util.List;

public interface FollowRepositoryCustom {

    List<FollowersResultDto> findFollowersByUserId(Long userId, Follow cursorId, Long size);

    List<FollowingsResultDto> findFollowingsByUserId(Long userId, Follow cursorId, Long size);
}
