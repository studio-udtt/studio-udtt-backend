package com.udtt.backend.project.entity;

import com.udtt.backend.global.entity.BaseTimeEntity;
import com.udtt.backend.project.enums.ProjectRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project_requests")
public class ProjectRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @Column(name = "requester_name", length = 50, nullable = false)
    private String requesterName;

    @Column(name = "requester_phone", length = 30, nullable = false)
    private String requesterPhone;

    @Column(name = "requester_email", length = 100)
    private String requesterEmail;

    @Column(name = "sms_agreed", nullable = false)
    private Boolean smsAgreed;

    @Column(name = "space_address", length = 255)
    private String spaceAddress;

    @Column(name = "region_sido", length = 50)
    private String regionSido;

    @Column(name = "region_sigungu", length = 50)
    private String regionSigungu;

    @Column(name = "project_type", length = 50)
    private String projectType;

    @Column(name = "space_size", length = 50)
    private String spaceSize;

    @Column(name = "desired_start_date")
    private LocalDate desiredStartDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private ProjectRequestStatus status = ProjectRequestStatus.PENDING;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void approve() {
        this.status = ProjectRequestStatus.APPROVED;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject(String rejectReason) {
        this.status = ProjectRequestStatus.REJECTED;
        this.rejectedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.status = ProjectRequestStatus.CANCELED;
    }
}