package com.funs.feedservice.domain.feed.service;

import com.funs.feedservice.domain.comment.dto.ForListInfoResponseDto;
import com.funs.feedservice.domain.feed.dto.DetailFeedResponseDto;
import com.funs.feedservice.domain.feed.dto.FeedResultDto;
import com.funs.feedservice.domain.feed.web.request.CreateFeedRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FeedService {
    void createFeed(Long userId, CreateFeedRequest content, MultipartFile image) throws IOException;

    void modifyFeed(Long userId, Long feedId, CreateFeedRequest content, MultipartFile image) throws IOException;

    void deleteFeed(Long userId, Long feedId);

    void likeFeed(Long userId, Long feedId);

    void unlikeFeed(Long userId, Long feedId);

    List<ForListInfoResponseDto> feedLikeList(Long userId, Long feedId, Long cursorId, Long size);

    List<FeedResultDto> newsfeed(Long userId, Long cursorId, Long size);

    List<FeedResultDto> adminNewsfeed(Long userId, Long cursorId, Long size);

    DetailFeedResponseDto feedDetail(Long userId, Long feedId);

    List<FeedResultDto> timeline(Long userId, Long cursorId, Long size);
}
