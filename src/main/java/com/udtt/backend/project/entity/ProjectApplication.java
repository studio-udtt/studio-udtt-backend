package com.udtt.backend.project.entity;

import com.udtt.backend.global.entity.BaseTimeEntity;
import com.udtt.backend.project.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project_applications")
public class ProjectApplication extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "applicant_name", length = 50, nullable = false)
    private String applicantName;

    @Column(name = "applicant_phone", length = 30, nullable = false)
    private String applicantPhone;

    @Column(name = "applicant_email", length = 100)
    private String applicantEmail;

    @Column(name = "reason", length = 100)
    private String reason;

    @Column(name = "job", length = 50)
    private String job;

    @Column(name = "sms_agreed", nullable = false)
    private Boolean smsAgreed;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}