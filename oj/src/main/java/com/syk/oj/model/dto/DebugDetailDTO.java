package com.syk.oj.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebugDetailDTO {
    private String input;

    private String output;

    private String expected;

    private String message;

    private Long time;
}
