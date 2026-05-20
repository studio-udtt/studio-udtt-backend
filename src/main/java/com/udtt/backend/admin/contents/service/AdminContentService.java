package com.udtt.backend.admin.contents.service;

import com.udtt.backend.admin.contents.dto.*;
import com.udtt.backend.admin.contents.repository.AdminContentRepository;
import com.udtt.backend.content.entity.Content;
import com.udtt.backend.content.enums.ContentStatus;
import com.udtt.backend.content.enums.ContentType;
import com.udtt.backend.content.enums.SourceType;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminContentService {

    private final AdminContentRepository adminContentRepository;

    private static final List<String> FIXED_KEYWORDS = List.of(
            "studio&lab 우당탕탕",
            "스튜디오 우당탕탕",
            "우당탕탕 프로젝트",
            "studio lab 우당탕탕"
    );

    private static final List<String> FIXED_SOURCE_URLS = List.of(
            "https://www.naver.com",
            "https://www.google.com"
            // 여기에 나중에 실제 수집하고 싶은 링크 추가
            // 예: "https://blog.naver.com/블로그아이디/글번호"
            // 예: "https://n.news.naver.com/article/..."
    );

    @Transactional(readOnly = true)
    public List<AdminContentListResponse> getContents(
            ContentType contentType,
            ContentStatus status,
            SourceType sourceType
    ) {
        List<Content> contents;

        if (contentType != null && status != null && sourceType != null) {
            contents = adminContentRepository.findByContentTypeAndStatusAndSourceType(contentType, status, sourceType);
        } else if (contentType != null && status != null) {
            contents = adminContentRepository.findByContentTypeAndStatus(contentType, status);
        } else if (contentType != null && sourceType != null) {
            contents = adminContentRepository.findByContentTypeAndSourceType(contentType, sourceType);
        } else if (status != null && sourceType != null) {
            contents = adminContentRepository.findByStatusAndSourceType(status, sourceType);
        } else if (contentType != null) {
            contents = adminContentRepository.findByContentType(contentType);
        } else if (status != null) {
            contents = adminContentRepository.findByStatus(status);
        } else if (sourceType != null) {
            contents = adminContentRepository.findBySourceType(sourceType);
        } else {
            contents = adminContentRepository.findAll();
        }

        return contents.stream()
                .map(AdminContentListResponse::from)
                .toList();
    }

    @Transactional
    public AdminContentCreateResponse createContent(AdminContentCreateRequest request) {
        Content content = Content.builder()
                .title(request.getTitle())
                .contentType(request.getContentType())
                .sourceType(request.getSourceType())
                .sourceName(request.getSourceName())
                .sourceUrl(request.getSourceUrl())
                .thumbnailUrl(request.getThumbnailUrl())
                .summary(request.getSummary())
                .body(request.getBody())
                .status(request.getStatus())
                .publishedAt(request.getStatus() == ContentStatus.PUBLISHED ? LocalDateTime.now() : null)
                .build();

        Content savedContent = adminContentRepository.save(content);

        return new AdminContentCreateResponse(
                savedContent.getId(),
                savedContent.getStatus(),
                "콘텐츠가 등록되었습니다."
        );
    }

    @Transactional(readOnly = true)
    public AdminContentDetailResponse getContent(Long contentId) {
        Content content = findContent(contentId);
        return AdminContentDetailResponse.from(content);
    }

    @Transactional
    public AdminContentUpdateResponse updateContent(Long contentId, AdminContentUpdateRequest request) {
        Content content = findContent(contentId);

        content.update(
                request.getTitle(),
                request.getSummary(),
                request.getBody(),
                request.getThumbnailUrl()
        );

        return new AdminContentUpdateResponse(
                content.getId(),
                "콘텐츠가 수정되었습니다."
        );
    }

    @Transactional
    public AdminContentStatusUpdateResponse updateContentStatus(
            Long contentId,
            AdminContentStatusUpdateRequest request
    ) {
        Content content = findContent(contentId);
        content.updateStatus(request.getStatus());

        return new AdminContentStatusUpdateResponse(
                content.getId(),
                content.getStatus(),
                "콘텐츠 게시 상태가 변경되었습니다."
        );
    }

    @Transactional
    public AdminContentDeleteResponse deleteContent(Long contentId) {
        Content content = findContent(contentId);
        content.delete();

        return new AdminContentDeleteResponse(
                contentId,
                "콘텐츠가 삭제되었습니다."
        );
    }

    @Transactional
    public AdminContentCrawlResponse crawlContent(AdminContentCrawlRequest request) {
        List<String> targetUrls = new ArrayList<>();

        // 1. 고정 링크 먼저 수집 대상에 추가
        targetUrls.addAll(FIXED_SOURCE_URLS);

        // 2. 고정 검색어로 네이버/구글 검색해서 나온 URL 추가
        for (String keyword : FIXED_KEYWORDS) {
            targetUrls.addAll(searchNaver(keyword));
            targetUrls.addAll(searchGoogle(keyword));
        }

        List<String> distinctUrls = targetUrls.stream()
                .filter(url -> url != null && !url.isBlank())
                .filter(this::isUsefulContentUrl)
                .distinct()
                .limit(10)
                .toList();

        int createdCount = 0;

        for (String url : distinctUrls) {
            try {
                Content content = crawlPage(url, request.getContentType());
                adminContentRepository.save(content);
                createdCount++;
            } catch (Exception e) {
                System.out.println("콘텐츠 수집 실패 url=" + url + ", reason=" + e.getMessage());
            }
        }

        return new AdminContentCrawlResponse(
                createdCount,
                "콘텐츠 자동 수집이 완료되었습니다."
        );
    }

    private List<String> searchNaver(String keyword) {
        try {
            String searchUrl = "https://search.naver.com/search.naver?query="
                    + URLEncoder.encode(keyword, StandardCharsets.UTF_8);

            Document document = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .referrer("https://www.naver.com")
                    .timeout(10000)
                    .get();

            return document.select("a[href]")
                    .stream()
                    .map(element -> element.attr("abs:href"))
                    .filter(this::isUsefulContentUrl)
                    .limit(5)
                    .toList();

        } catch (Exception e) {
            System.out.println("네이버 검색 실패 keyword=" + keyword + ", reason=" + e.getMessage());
            return List.of();
        }
    }

    private List<String> searchGoogle(String keyword) {
        try {
            String searchUrl = "https://www.google.com/search?q="
                    + URLEncoder.encode(keyword, StandardCharsets.UTF_8);

            Document document = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .referrer("https://www.google.com")
                    .timeout(10000)
                    .get();

            return document.select("a[href]")
                    .stream()
                    .map(element -> element.attr("href"))
                    .map(this::extractGoogleUrl)
                    .filter(this::isUsefulContentUrl)
                    .limit(5)
                    .toList();

        } catch (Exception e) {
            System.out.println("구글 검색 실패 keyword=" + keyword + ", reason=" + e.getMessage());
            return List.of();
        }
    }

    private Content crawlPage(String url, ContentType contentType) throws Exception {
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .referrer("https://www.google.com")
                .timeout(10000)
                .get();

        String title = getMetaContent(document, "meta[property=og:title]");
        if (isBlank(title)) {
            title = getMetaContent(document, "meta[name=twitter:title]");
        }
        if (isBlank(title)) {
            title = document.title();
        }
        if (isBlank(title)) {
            title = "자동 수집 콘텐츠";
        }

        String summary = getMetaContent(document, "meta[property=og:description]");
        if (isBlank(summary)) {
            summary = getMetaContent(document, "meta[name=description]");
        }
        if (isBlank(summary)) {
            summary = getMetaContent(document, "meta[name=twitter:description]");
        }

        String thumbnailUrl = getMetaContent(document, "meta[property=og:image]");
        if (isBlank(thumbnailUrl)) {
            thumbnailUrl = getMetaContent(document, "meta[name=twitter:image]");
        }

        String body = extractBody(document, summary);

        return Content.builder()
                .title(title)
                .contentType(contentType)
                .sourceType(SourceType.CRAWLED)
                .sourceName(getSourceNameFromUrl(url))
                .sourceUrl(url)
                .thumbnailUrl(thumbnailUrl)
                .summary(summary)
                .body(body)
                .status(ContentStatus.DRAFT)
                .crawledAt(LocalDateTime.now())
                .build();
    }

    private Content findContent(Long contentId) {
        return adminContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 콘텐츠입니다. contentId=" + contentId));
    }

    private String getMetaContent(Document document, String cssQuery) {
        Element element = document.selectFirst(cssQuery);

        if (element == null) {
            return null;
        }

        String content = element.attr("content");

        if (isBlank(content)) {
            return null;
        }

        return content.trim();
    }

    private String extractBody(Document document, String summary) {
        String articleText = document.select("article").text();

        if (!isBlank(articleText)) {
            return limitLength(articleText, 3000);
        }

        String mainText = document.select("main").text();

        if (!isBlank(mainText)) {
            return limitLength(mainText, 3000);
        }

        String paragraphText = document.select("p").text();

        if (!isBlank(paragraphText)) {
            return limitLength(paragraphText, 3000);
        }

        return summary;
    }

    private String limitLength(String text, int maxLength) {
        if (text == null) {
            return null;
        }

        if (text.length() <= maxLength) {
            return text;
        }

        return text.substring(0, maxLength);
    }

    private String extractGoogleUrl(String href) {
        if (href == null) {
            return null;
        }

        if (href.startsWith("/url?q=")) {
            String withoutPrefix = href.substring("/url?q=".length());
            int endIndex = withoutPrefix.indexOf("&");

            if (endIndex > 0) {
                return withoutPrefix.substring(0, endIndex);
            }

            return withoutPrefix;
        }

        if (href.startsWith("http")) {
            return href;
        }

        return null;
    }

    private boolean isUsefulContentUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        if (!url.startsWith("http")) {
            return false;
        }

        return !url.contains("google.com")
                && !url.contains("naver.com/search")
                && !url.contains("search.naver.com")
                && !url.contains("accounts.google.com")
                && !url.contains("support.google.com")
                && !url.contains("webcache.googleusercontent.com")
                && !url.contains("javascript:")
                && !url.contains("#");
    }

    private String getSourceNameFromUrl(String url) {
        if (url.contains("naver.com")) {
            return "네이버";
        }

        if (url.contains("google.com")) {
            return "구글";
        }

        if (url.contains("instagram.com")) {
            return "인스타그램";
        }

        if (url.contains("blog")) {
            return "블로그";
        }

        if (url.contains("news")) {
            return "뉴스";
        }

        return "자동 수집";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}