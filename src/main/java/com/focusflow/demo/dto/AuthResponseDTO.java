package com.focusflow.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseDTO
{
    private String accessToken;
    private String refreshToken;
    private UserDTO user; // so the frontend has profile info right after login, no extra call

}
