package com.funs.userservice.domain.user.dto;


import com.funs.userservice.domain.user.entity.AuthType;
import com.funs.userservice.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthAttributeDto {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저정보 MAP
    private String nameAttributeKey;
    private String name;
    private String email;
    private AuthType authType;

    public OAuthAttributeDto(Map<String, Object> attributes, String nameAttributeKey, String name, String email){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributeDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId))
            return ofNaver(userNameAttributeName, attributes);

        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuthAttributeDto ofNaver(String userNameAttributeName, Map<String, Object> attribute) {
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");
        return OAuthAttributeDto.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .nameAttributeKey("id")
                .attributes(response)
                .authType(AuthType.NAVER)
                .build();

    }

    public static OAuthAttributeDto ofKakao(String userNameAttributeName, Map<String, Object> attribute) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return OAuthAttributeDto.builder()
                .name((String) profile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .nameAttributeKey("id")
                .attributes(attribute)
                .build();
    }



    public User toEntity(OAuthAttributeDto oAuthAttributeDto){
        return new User(email, "temp", name, null, null, oAuthAttributeDto.getAuthType() );
    }
}
