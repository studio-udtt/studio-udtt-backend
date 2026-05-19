package com.udtt.backend.content.repository;

import com.udtt.backend.content.entity.Content;
import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByStatusAndDeletedAtIsNullOrderByPublishedAtDesc(ContentStatus status);

    List<Content> findByContentTypeAndStatusAndDeletedAtIsNullOrderByPublishedAtDesc(
            ContentType contentType,
            ContentStatus status
    );

    Optional<Content> findByIdAndStatusAndDeletedAtIsNull(Long id, ContentStatus status);
}