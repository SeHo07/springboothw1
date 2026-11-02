package com.likelion.springsession.auth.dto;

import lombok.*;

// auth/dto/TokenResponse.java
@Getter
@RequiredArgsConstructor
public class TokenResponse {

    private final String accessToken;
}