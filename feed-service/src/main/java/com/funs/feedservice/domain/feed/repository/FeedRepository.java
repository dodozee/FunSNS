package com.funs.feedservice.domain.feed.repository;

import com.funs.feedservice.domain.feed.entity.Feed;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {

}
