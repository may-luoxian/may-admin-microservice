package com.syk.oj.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CalendarDTO {

    private String submitNum;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private LocalDateTime date;
}
