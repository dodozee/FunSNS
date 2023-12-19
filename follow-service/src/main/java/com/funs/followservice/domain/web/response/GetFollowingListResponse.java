package com.funs.followservice.domain.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFollowingListResponse {
    private Long userId;
    private List<Long> followingList;
}
