package com.funs.feedservice.domain.comment.repository;

import com.funs.feedservice.domain.comment.dto.CommentResponseDto;
import com.funs.feedservice.domain.comment.dto.QCommentResponseDto;
import com.funs.feedservice.domain.comment.dto.SearchCommentConditionDto;
import com.funs.feedservice.domain.comment.entity.Comment;
import com.funs.feedservice.domain.comment.service.CommentTargetType;
import com.funs.feedservice.global.client.user.UserClient;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.funs.feedservice.domain.comment.entity.QComment.comment;
import static com.querydsl.core.types.ExpressionUtils.and;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final CommentLikeRepository commentLikeRepository;
    private final UserClient userClient;


    @Override
    public List<CommentResponseDto> searchCommentList(Long userId, SearchCommentConditionDto searchCommentConditionDto, Comment cursorComment) {


        List<CommentResponseDto> content = queryFactory
                .select(new QCommentResponseDto(
                            comment.id,
                            comment.content,
                            comment.commentLikes.size(),
                            comment.children.size(),
                            comment.userId,
                            comment.nickname,
                            Expressions.asBoolean(false),//좋아요 여부
                            comment.createdAt
                        ))
                .from(comment)
                .where(
//                        comment.userId.eq(userId), //본인 댓글만 보이게 되는 이유
                        target(searchCommentConditionDto.getTargetType(), searchCommentConditionDto.getTargetId()),
                        cursorPagination(cursorComment)
                )
                .limit(searchCommentConditionDto.getSize())
                .fetch();
        if(userId != null) {
            for(CommentResponseDto commentResponseDto : content) {

                String profileImgURL = userClient.getUserProfileById(String.valueOf(commentResponseDto.getUserId())).getData().getProfileImgURL();
                commentResponseDto.setProfileImgURL(profileImgURL);
                if(commentLikeRepository.existsByCommentLikeUserIdAndCommentId(userId, commentResponseDto.getCommentId())) {
                    commentResponseDto.setIsLiked(true);
                }
            }
        }

        return content;
    }

    private Predicate cursorPagination(Comment cursorComment) {
        if(cursorComment == null) {
            return null;
        }
        return comment.id.gt(cursorComment.getId());
    }

    private Predicate target(CommentTargetType targetType, Long targetId) {
        if(targetType == null || targetId == 0) {
            return null;
        }
        switch (targetType) {
            case FEED:
                return and(comment.feed.id.eq(targetId), comment.parent.isNull());
            case COMMENT:
                return comment.parent.id.eq(targetId);
        }
        return null;

    }
}
