package com.funs.followservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @Column(name = "from_user_id")
    private Long fromUserId;

    @Column(name = "from_user_nickname")
    private String fromUserNickname;

    @Column(name = "to_user_id")
    private Long toUserId;

    @Column(name = "to_user_nickname")
    private String toUserNickname;

    @Column(name = "follow_time")
    private LocalDateTime followTime;

    public static Follow of(Long fromUserId,String fromUserNickname, Long toUserId, String toUserNickname){
        Follow follow = new Follow();
        follow.fromUserId = fromUserId;
        follow.fromUserNickname = fromUserNickname;
        follow.toUserId = toUserId;
        follow.toUserNickname = toUserNickname;
        follow.followTime = LocalDateTime.now();
        return follow;
    }

    public static Follow createFollow(Long fromUserId, String fromUserNickname, Long toUserId, String toUserNickname) {
        return Follow.of(fromUserId, fromUserNickname, toUserId, toUserNickname);
    }
}
