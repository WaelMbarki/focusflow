package com.focusflow.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO
{
    private String id;
    private String email;
    private String fullName;
    private LocalDate birthDate;

}
