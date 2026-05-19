package com.udtt.backend.content.service;

import com.udtt.backend.content.dto.ContentDetailResponse;
import com.udtt.backend.content.dto.ContentListResponse;
import com.udtt.backend.content.entity.Content;
import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import com.udtt.backend.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final ContentRepository contentRepository;

    public List<ContentListResponse> getContents(ContentType contentType, ContentStatus status) {
        ContentStatus searchStatus = status == null ? ContentStatus.PUBLISHED : status;

        List<Content> contents;

        if (contentType == null) {
            contents = contentRepository.findByStatusAndDeletedAtIsNullOrderByPublishedAtDesc(searchStatus);
        } else {
            contents = contentRepository.findByContentTypeAndStatusAndDeletedAtIsNullOrderByPublishedAtDesc(
                    contentType,
                    searchStatus
            );
        }

        return contents.stream()
                .map(ContentListResponse::from)
                .toList();
    }

    public ContentDetailResponse getContent(Long contentId) {
        Content content = contentRepository
                .findByIdAndStatusAndDeletedAtIsNull(contentId, ContentStatus.PUBLISHED)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠를 찾을 수 없습니다."));

        return ContentDetailResponse.from(content);
    }
}