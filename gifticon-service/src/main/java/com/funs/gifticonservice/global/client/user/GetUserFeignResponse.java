package com.funs.gifticonservice.global.client.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserFeignResponse {
    private Long userId;
    private String nickname;
    private String profileImage;
}
