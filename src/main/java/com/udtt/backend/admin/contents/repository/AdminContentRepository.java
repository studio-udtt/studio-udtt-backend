package com.udtt.backend.admin.contents.repository;

import com.udtt.backend.content.entity.Content;
import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import com.udtt.backend.content.enums.SourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByContentTypeAndStatusAndSourceType(
            ContentType contentType,
            ContentStatus status,
            SourceType sourceType
    );

    List<Content> findByContentTypeAndStatus(ContentType contentType, ContentStatus status);

    List<Content> findByContentTypeAndSourceType(ContentType contentType, SourceType sourceType);

    List<Content> findByStatusAndSourceType(ContentStatus status, SourceType sourceType);

    List<Content> findByContentType(ContentType contentType);

    List<Content> findByStatus(ContentStatus status);

    List<Content> findBySourceType(SourceType sourceType);
}