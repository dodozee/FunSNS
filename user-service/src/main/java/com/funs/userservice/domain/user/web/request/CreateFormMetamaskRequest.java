package com.funs.userservice.domain.user.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFormMetamaskRequest {
    private String nickname;//닉네임
    private String metamaskAddress; //메타마스크 주소

}
