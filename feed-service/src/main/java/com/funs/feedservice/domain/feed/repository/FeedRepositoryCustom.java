package com.funs.feedservice.domain.feed.repository;

import com.funs.feedservice.domain.feed.dto.FeedResultDto;
import com.funs.feedservice.domain.feed.entity.Feed;

import java.util.List;

public interface FeedRepositoryCustom {
    List<FeedResultDto> searchFeedBy(Long userId, List<Long> followings, Feed cursorFeed, Long size);
}
