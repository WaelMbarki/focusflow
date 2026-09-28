package com.focusflow.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectResponseDTO
{
    private String id;
    private String name;
    private String color;
}
