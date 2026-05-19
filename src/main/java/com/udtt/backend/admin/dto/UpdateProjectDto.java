package com.udtt.backend.admin.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProjectDto {

    private String title;
    private String summary;
    private String description;
    private String project_type;
    private String space_size;
    private String address;
    private String region_sido;
    private String region_sigungu;
    private LocalDate recruit_start_date;
    private LocalDate recruit_end_date;
    private LocalDate project_start_date;
    private LocalDate project_end_date;
    private Integer max_participants;
    private Boolean is_visible;
}
