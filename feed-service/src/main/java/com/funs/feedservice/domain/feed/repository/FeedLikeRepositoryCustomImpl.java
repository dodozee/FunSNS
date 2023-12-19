package com.funs.feedservice.domain.feed.repository;

import com.funs.feedservice.domain.comment.dto.ForListInfoResponseDto;
import com.funs.feedservice.domain.comment.dto.QForListInfoResponseDto;
import com.funs.feedservice.domain.feed.entity.Feed;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.funs.feedservice.domain.feed.entity.QFeedLike.feedLike;
import static com.funs.feedservice.domain.feed.entity.QFeedLikeUser.feedLikeUser;

@Repository
@RequiredArgsConstructor
public class FeedLikeRepositoryCustomImpl implements FeedLikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ForListInfoResponseDto> feedLikeList(Feed feed, Long cursorId, Long size) {
        List<ForListInfoResponseDto> content = queryFactory
                .select(new QForListInfoResponseDto(
                        feedLike.id,
                        feedLike.feedLikeUser.userId,
                        feedLike.feedLikeUser.nickname
                ))
                .from(feedLike)
                .leftJoin(feedLike.feedLikeUser,feedLikeUser)
                .where(
                        cursorPagination(cursorId),
                        feedLike.feed.eq(feed)
                )
                .limit(size)
                .fetch();

        return content;
    }

    private Predicate cursorPagination(Long cursorId) {
        if(cursorId == null || cursorId == 0) {
            return null;
        }
        return feedLike.id.lt(cursorId);
    }
}
