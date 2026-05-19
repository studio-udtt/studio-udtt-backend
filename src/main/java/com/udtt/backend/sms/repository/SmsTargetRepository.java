package com.udtt.backend.sms.repository;

import com.udtt.backend.sms.dto.SmsTargetResponse;
import com.udtt.backend.sms.enums.SmsRecipientType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SmsTargetRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SmsTargetResponse> findApplicantTargets(Long projectId, Boolean smsAgreed) {
        String sql = """
                SELECT
                    application_id,
                    applicant_name,
                    applicant_phone,
                    sms_agreed
                FROM project_applications
                WHERE deleted_at IS NULL
                  AND (:projectId IS NULL OR project_id = :projectId)
                  AND (:smsAgreed IS NULL OR sms_agreed = :smsAgreed)
                ORDER BY application_id DESC
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("projectId", projectId);
        query.setParameter("smsAgreed", smsAgreed);

        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(row -> new SmsTargetResponse(
                        SmsRecipientType.APPLICANT,
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (Boolean) row[3]
                ))
                .toList();
    }

    public List<SmsTargetResponse> findRequesterTargets(Long projectId, Boolean smsAgreed) {
        String sql = """
                SELECT
                    pr.request_id,
                    pr.requester_name,
                    pr.requester_phone,
                    pr.sms_agreed
                FROM project_requests pr
                JOIN projects p ON p.request_id = pr.request_id
                WHERE pr.deleted_at IS NULL
                  AND p.deleted_at IS NULL
                  AND (:projectId IS NULL OR p.project_id = :projectId)
                  AND (:smsAgreed IS NULL OR pr.sms_agreed = :smsAgreed)
                ORDER BY pr.request_id DESC
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("projectId", projectId);
        query.setParameter("smsAgreed", smsAgreed);

        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(row -> new SmsTargetResponse(
                        SmsRecipientType.REQUESTER,
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (Boolean) row[3]
                ))
                .toList();
    }
}