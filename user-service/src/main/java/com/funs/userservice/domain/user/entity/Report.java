package com.funs.userservice.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Report {
    @Id @GeneratedValue
    @Column(name = "report_id")
    private Long id;
    @Column(name = "reporter_user_id")
    private Long reporterUserId;
    @Column(name = "reporter_user_nickname")
    private String reporterUserNickname;
    @Column(name = "reported_user_id")
    private Long reportedUserId;
    @Column(name = "reported_user_nickname")
    private String reportedUserNickname;
    private String reason;
    @Column(name = "is_proceeded")
    boolean isProceeded;
    private LocalDateTime createdAt;

    @Builder
    public Report(Long reporterUserId, String reporterUserNickname, Long reportedUserId, String reportedUserNickname, String reason) {
        this.reporterUserId = reporterUserId;
        this.reporterUserNickname = reporterUserNickname;
        this.reportedUserId = reportedUserId;
        this.reportedUserNickname = reportedUserNickname;
        this.reason = reason;
        this.isProceeded = false;
        this.createdAt = LocalDateTime.now();
    }

    public static Report of(Long reporterUserId, String reporterUserNickname, Long reportedUserId, String reportedUserNickname, String reason) {
        Report report = new Report();
        report.reporterUserId = reporterUserId;
        report.reporterUserNickname = reporterUserNickname;
        report.reportedUserId = reportedUserId;
        report.reportedUserNickname = reportedUserNickname;
        report.reason = reason;
        report.isProceeded = false;
        report.createdAt = LocalDateTime.now();

        return report;
    }
}
