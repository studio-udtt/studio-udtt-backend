package com.udtt.backend.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProjectStatusDto {

    @NotBlank
    private String status; // RECRUITING, IN_PROGRESS, COMPLETED, CANCELED
}
