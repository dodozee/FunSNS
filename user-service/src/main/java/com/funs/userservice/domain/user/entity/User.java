package com.funs.userservice.domain.user.entity;

import com.funs.userservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String area;
    @Column(name = "profile_image")
    private String profileImage;
    private String introduction;
    @Column(name = "login_count")
    private Long loginCount;

    @Column(name = "user_type")
    private String userType;

    @Column(insertable = false, updatable = false)
    protected String dtype;

    @Enumerated(EnumType.STRING)
    private AuthType oauthType;

    @Column(name = "metamask_address", length = 500)
    private String metamaskAddress;


    public User(String email, String password, String name, String nickname, String area, AuthType oauthType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.area = area;
        this.dtype = User.class.getSimpleName();
        this.oauthType = oauthType;
        this.loginCount = 0L;
    }

    public void addUserProfile(String nickname, String address) {
        this.nickname = nickname;
        this.area = address;
        this.profileImage = "https://with-sports-s3.s3.ap-northeast-2.amazonaws.com/static/a0620005-d1bf-499a-8126-0a3e9377ddb9%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.png_1702454448961.png";
        this.introduction = "안녕하세요^ㅇ^! 새로운 가천인 입니다.^^";
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateUserProfile(String nickname, String introduction, String userType) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.userType = userType;

    }
    public void updateLoginCount() {
        this.loginCount++;
    }

    public void addUserBasicProfileImage() {
        this.profileImage = "https://with-sports-s3.s3.ap-northeast-2.amazonaws.com/static/a0620005-d1bf-499a-8126-0a3e9377ddb9%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.png_1702454448961.png";
    }

    public void registerMetamaskAddress(String metamaskAddress) {
        this.metamaskAddress = metamaskAddress;
    }
}
