package com.siddarthavadavalasa.SwiftCollab.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {
    private String email;
    private String password;
}
