package com.udtt.backend.admin.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApproveProjectRequestDto {

    @NotBlank
    private String title;
    @NotBlank
    private String summary;
    @NotBlank
    private String description;

    @NotNull
    private LocalDate recruit_start_date;
    @NotNull
    private LocalDate recruit_end_date;
    @NotNull
    private LocalDate project_start_date;
    @NotNull
    private LocalDate project_end_date;

    @NotNull
    private Integer max_participants;
    @NotNull
    private BigDecimal latitude;
    @NotNull
    private BigDecimal longitude;

    private Boolean is_visible = true;
}