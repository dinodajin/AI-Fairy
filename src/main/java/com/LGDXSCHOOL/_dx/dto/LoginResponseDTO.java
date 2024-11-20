package com.LGDXSCHOOL._dx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// 로그인 응답 DTO
@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
}
