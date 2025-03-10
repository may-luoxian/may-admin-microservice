package com.may.managementcenter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeEnableAndNotEnableDTO {
    private List<HomeDTO> enableList;

    private List<HomeDTO> notEnableList;
}
