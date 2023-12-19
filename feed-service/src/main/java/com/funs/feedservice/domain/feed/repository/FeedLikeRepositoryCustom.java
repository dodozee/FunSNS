package com.funs.feedservice.domain.feed.repository;

import com.funs.feedservice.domain.comment.dto.ForListInfoResponseDto;
import com.funs.feedservice.domain.feed.entity.Feed;

import java.util.List;

public interface FeedLikeRepositoryCustom {
    List<ForListInfoResponseDto> feedLikeList(Feed feed, Long cursorId, Long size);

}
