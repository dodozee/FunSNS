package com.funs.feedservice.domain.feed.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFeedRequest {
    private String title; //제목
    private String content; //내용
}
