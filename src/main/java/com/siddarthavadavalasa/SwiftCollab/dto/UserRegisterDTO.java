package com.siddarthavadavalasa.SwiftCollab.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {
    private String username;
    private String email;
    private String password;
}
