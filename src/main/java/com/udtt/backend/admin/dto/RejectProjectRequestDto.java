package com.udtt.backend.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RejectProjectRequestDto {

    @NotBlank
    private String reject_reason;
}
