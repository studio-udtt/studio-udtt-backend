package com.udtt.backend.stat.controller;

import com.udtt.backend.stat.dto.SiteStatResponse;
import com.udtt.backend.stat.service.SiteStatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Site Stats", description = "누적 데이터 조회 API")
@RestController
@RequestMapping("/api/v1/site-stats")
@RequiredArgsConstructor
public class SiteStatController {

    private final SiteStatService siteStatService;

    @Operation(
            summary = "누적 데이터 조회",
            description = "누적 참여자 수, 스텝/강사 수, 누적 프로젝트 수 등 사이트에 표시할 누적 데이터를 조회합니다."
    )
    @GetMapping
    public List<SiteStatResponse> getSiteStats() {
        return siteStatService.getSiteStats();
    }
}