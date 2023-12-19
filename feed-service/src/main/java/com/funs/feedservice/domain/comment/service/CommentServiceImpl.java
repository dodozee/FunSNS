package com.funs.feedservice.domain.comment.service;

import com.funs.feedservice.domain.comment.dto.*;
import com.funs.feedservice.domain.comment.entity.Comment;
import com.funs.feedservice.domain.comment.entity.CommentLike;
import com.funs.feedservice.domain.comment.entity.CommentLikeUser;
import com.funs.feedservice.domain.comment.exception.NotExistCommentException;
import com.funs.feedservice.domain.comment.repository.CommentLikeUserRepository;
import com.funs.feedservice.domain.comment.repository.CommentRepository;
import com.funs.feedservice.domain.feed.entity.Feed;
import com.funs.feedservice.domain.feed.exception.AlreadyLikedException;
import com.funs.feedservice.domain.feed.exception.NotExistFeedException;
import com.funs.feedservice.domain.comment.repository.CommentLikeRepository;
import com.funs.feedservice.domain.feed.repository.FeedRepository;
import com.funs.feedservice.global.client.user.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeUserRepository commentLikeUserRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserClient userClient;


    @Transactional
    @Override
    public void addComment(Long userId, Long feedId, AddCommentDto addCommentDto) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NotExistFeedException("해당 피드가 존재하지 않습니다."));
        String nickname = userClient.getNicknameUserById(String.valueOf(userId)).getData().getNickname();
        Comment comment = Comment.createComment(userId,nickname, addCommentDto.getContent());
        feed.addComment(comment);

        commentRepository.save(comment);


    }

    @Transactional
    @Override
    public void replyComment(Long userId, Long commentId, ReplyCommentDto replyCommentDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotExistCommentException("해당 댓글이 존재하지 않습니다."));
        String nickname = userClient.getNicknameUserById(String.valueOf(userId)).getData().getNickname();

        Comment reply = Comment.createReply(userId, nickname, comment.getFeed(), comment, replyCommentDto.getContent());
        comment.addChildren(reply);
        commentRepository.save(reply);

    }

    @Transactional
    @Override
    public void updateCommentOrReply(Long userId, Long commentId, UpdateCommentDto updateCommentDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotExistCommentException("해당 댓글이 존재하지 않습니다."));

        if(!Objects.equals(comment.getUserId(), userId)){
            throw new NotExistCommentException("해당 댓글의 작성자가 아닙니다.");
        }

        comment.updateComment(updateCommentDto.getContent());
    }

    @Transactional
    @Override
    public void deleteCommentOrReply(Long userId, Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotExistCommentException("해당 댓글이 존재하지 않습니다."));

        if(!Objects.equals(comment.getUserId(), userId)){
            throw new NotExistCommentException("해당 댓글의 작성자가 아닙니다.");
        }

        commentRepository.delete(comment);
    }

    @Transactional
    @Override
    public void likeComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotExistCommentException("해당 댓글이 존재하지 않습니다."));
//        CommentLikeUser commentLikeUser = commentLikeUserRepository.findByUserId(userId).orElseThrow(() -> new NotExistCommentException("좋아요 누른 사용자가 존재하지 않습니다."));
        if(1<=commentLikeRepository.countLikesByUserIdAndCommentId(userId, commentId)){
            throw new AlreadyLikedException("이미 좋아요를 누른 댓글입니다.");
        }
        CommentLikeUser commentLikeUser = CommentLikeUser.createCommentLikeUser(userId, userClient.getNicknameUserById(String.valueOf(userId)).getData().getNickname());
        commentLikeUserRepository.save(commentLikeUser);



        CommentLike commentLike = CommentLike.createCommentLike(commentLikeUser, comment);
        commentLikeRepository.save(commentLike);
    }

    @Transactional
    @Override
    public void unlikeComment(Long userId, Long commentId) {
//        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotExistCommentException("해당 댓글이 존재하지 않습니다."));
//        CommentLikeUser commentLikeUser = commentLikeUserRepository.findByUserId(userId).orElseThrow(() -> new NotExistCommentException("해당 유저가 존재하지 않습니다."));

        if(0==commentLikeRepository.countLikesByUserIdAndCommentId(userId, commentId)){
            throw new AlreadyLikedException("좋아요를 누르지 않은 댓글입니다.");
        }
        CommentLike commentLike = commentLikeRepository.findByUserIdAndCommentId(userId, commentId).orElseThrow(() -> new NotExistCommentException("해당 좋아요가 존재하지 않습니다."));
        commentLikeRepository.delete(commentLike);
    }

    @Override
    public List<ForListInfoResponseDto> commentLikeList(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotExistCommentException("해당 댓글이 존재하지 않습니다."));

        List<CommentLike> commentLikeList = commentLikeRepository.findAllByComment(comment);
        List<ForListInfoResponseDto> forListInfoResponseDtoList = new ArrayList<>();

        if (userId == null) {
            for (CommentLike commentLike : commentLikeList) {
                CommentLikeUser commentLikeUser = commentLike.getCommentLikeUser();
                String profileImgURL = userClient.getUserProfileById(String.valueOf(commentLike.getCommentLikeUser().getUserId())).getData().getProfileImgURL();
                ForListInfoResponseDto forListInfoResponseDto = new ForListInfoResponseDto(null, commentLikeUser.getUserId(), commentLikeUser.getNickname());
                forListInfoResponseDto.setProfileImgURL(profileImgURL);

                forListInfoResponseDtoList.add(forListInfoResponseDto);
            }
        } else {
            CommentLikeUser commentLikeUser = commentLikeUserRepository.findByUserId(userId).orElseThrow(() -> new NotExistCommentException("해당 유저가 존재하지 않습니다."));
            for (CommentLike commentLike : commentLikeList) {
                CommentLikeUser likedUser = commentLike.getCommentLikeUser();
                String profileImgURL = userClient.getUserProfileById(String.valueOf(commentLike.getCommentLikeUser().getUserId())).getData().getProfileImgURL();
                ForListInfoResponseDto forListInfoResponseDto = new ForListInfoResponseDto(null, commentLikeUser.getUserId(), commentLikeUser.getNickname());
                forListInfoResponseDto.setProfileImgURL(profileImgURL);

                forListInfoResponseDtoList.add(forListInfoResponseDto);
            }

        }


        return forListInfoResponseDtoList;
    }

    @Override
    public List<CommentResponseDto> getCommentList(SearchCommentConditionDto searchCommentConditionDto, Long userId, Long cursorId) {

        Comment cursorComment = null;

        if(cursorId != null && cursorId != 0){
            cursorComment = commentRepository.findById(cursorId).orElseThrow(() -> new NotExistCommentException("해당 댓글이 존재하지 않습니다."));
        }

        switch (searchCommentConditionDto.getTargetType()) {
            case FEED -> {
                if (!feedRepository.existsById(searchCommentConditionDto.getTargetId())) {
                    throw new NotExistFeedException("해당 피드가 존재하지 않습니다.");
                }
            }
            case COMMENT -> {
                if (!commentRepository.existsById(searchCommentConditionDto.getTargetId())) {
                    throw new NotExistCommentException("해당 댓글이 존재하지 않습니다.");
                }
            }
        }
        return commentRepository.searchCommentList(userId, searchCommentConditionDto, cursorComment);
    }
}
