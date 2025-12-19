package com.siddarthavadavalasa.SwiftCollab.service;

import com.siddarthavadavalasa.SwiftCollab.dto.AuthResponseDTO;
import com.siddarthavadavalasa.SwiftCollab.dto.UserLoginDTO;
import com.siddarthavadavalasa.SwiftCollab.dto.UserRegisterDTO;
import com.siddarthavadavalasa.SwiftCollab.dto.UserResponseDTO;
import com.siddarthavadavalasa.SwiftCollab.model.Role;
import com.siddarthavadavalasa.SwiftCollab.model.User;
import com.siddarthavadavalasa.SwiftCollab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public UserResponseDTO registerUser(UserRegisterDTO dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .build();

        User saved = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .role(saved.getRole().name())
                .build();
    }

    // 🟢 Login logic
    // public String loginUser(UserLoginDTO dto) {
    //     User user = userRepository.findByEmail(dto.getEmail())
    //             .orElseThrow(() -> new RuntimeException("User not found"));

    //     if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
    //         throw new RuntimeException("Invalid password");
    //     }

    //     // Generate JWT token
    //     return jwtService.generateToken(user.getUsername());
    // }
    public AuthResponseDTO loginUser(UserLoginDTO dto) {
    User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid password");
    }

    String accessToken = jwtService.generateToken(user.getUsername());
    String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

    return new AuthResponseDTO(accessToken, refreshToken);
}

}
