package com.focusflow.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyFocusPointDTO
{
    private LocalDate date;
    private Integer focusSeconds;

}
