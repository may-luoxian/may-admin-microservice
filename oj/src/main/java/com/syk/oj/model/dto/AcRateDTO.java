package com.syk.oj.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AcRateDTO {
    private Integer accept;

    private Integer sum;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private LocalDateTime createTime;
}
