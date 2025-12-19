package com.siddarthavadavalasa.SwiftCollab.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
}
