package com.funs.followservice.domain.repository;

import com.funs.followservice.domain.dto.FollowersResultDto;
import com.funs.followservice.domain.dto.FollowingsResultDto;
import com.funs.followservice.domain.dto.QFollowersResultDto;
import com.funs.followservice.domain.dto.QFollowingsResultDto;
import com.funs.followservice.domain.entity.Follow;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.funs.followservice.domain.entity.QFollow.follow;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryCustomImpl implements FollowRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<FollowersResultDto> findFollowersByUserId(Long userId, Follow cursorId, Long size) {

        return queryFactory
                .select(new QFollowersResultDto( //이 사람을 따르는 사람들을 조회해서 보고싶은거
                        follow.fromUserId,
                        follow.fromUserNickname
                ))
                .from(follow)
                .where(follow.toUserId.eq(userId),
                        cursorPagination(cursorId))
                .limit(size)
                .orderBy(follow.followTime.desc())
                .fetch();
    }


    @Override
    public List<FollowingsResultDto> findFollowingsByUserId(Long userId, Follow cursorId, Long size) {
        return queryFactory
                .select(new QFollowingsResultDto( //이 사람을 따르는 사람들을 조회해서 보고싶은거
                        follow.toUserId,
                        follow.toUserNickname
                ))
                .from(follow)
                .where(follow.fromUserId.eq(userId),
                        cursorPagination(cursorId))
                .limit(size)
                .orderBy(follow.followTime.desc())
                .fetch();
    }

    private Predicate cursorPagination(Follow cursorId) {

            if(cursorId == null) {
                return null;
            }

            return follow.id.lt(cursorId.getId());
    }

}
