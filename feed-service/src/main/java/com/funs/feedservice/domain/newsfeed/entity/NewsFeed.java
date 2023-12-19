package com.funs.feedservice.domain.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "news_feed", indexes = {@Index(name = "idx_news_feed_user_id", columnList = "user_id")})
public class NewsFeed {

    @Id @GeneratedValue
    @Column(name = "news_feed_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "feed_id")
    private Long feedId;




}
