package com.funs.feedservice.domain.feed.repository;

import com.funs.feedservice.domain.feed.dto.FeedResultDto;
import com.funs.feedservice.domain.feed.dto.QFeedResultDto;
import com.funs.feedservice.domain.feed.entity.Feed;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.funs.feedservice.domain.feed.entity.QFeed.feed;

@Repository
@RequiredArgsConstructor
public class FeedRepositoryCustomImpl implements FeedRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<FeedResultDto> searchFeedBy(Long userId,List<Long> followings, Feed cursorFeed, Long size) {
        return getBaseQuery(userId, followings, cursorFeed, size)
                .orderBy(feed.createdAt.desc())
                .fetch();


    }

    private JPQLQuery<FeedResultDto> getBaseQuery(Long userId, List<Long> followings, Feed cursorFeed, Long size){

        return queryFactory
                .select(new QFeedResultDto(
                        feed.id,//피드 아이디
                        feed.userId,//유저 아이디
                        feed.nickname,//닉네임
                        feed.title,//제목
                        feed.content,//내용
                        feed.imageUrl,//이미지
                        feed.feedLikes.size(),//좋아요 수
                        feed.commentLists.size(),//댓글 수
                        Expressions.asBoolean(false), //좋아요 여부
                        feed.createdAt

                ))
                .from(feed)
                .where(feed.userId.in(followings),
//                        feed.createdAt.lt(cursorFeed.getCreatedAt()),
//                        feed.id.lt(cursorFeed.getId()))
                        cursorPagination(cursorFeed))
                .limit(size);
    }

    private Predicate cursorPagination(Feed cursorPost) {

        if(cursorPost == null) {
            return null;
        }


        return feed.id.lt(cursorPost.getId());

    }

}
