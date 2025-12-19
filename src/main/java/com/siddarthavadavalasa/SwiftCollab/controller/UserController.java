package com.siddarthavadavalasa.SwiftCollab.controller;
import com.siddarthavadavalasa.SwiftCollab.service.JwtService;
import com.siddarthavadavalasa.SwiftCollab.service.RefreshTokenService;
import com.siddarthavadavalasa.SwiftCollab.service.UserService;
import com.siddarthavadavalasa.SwiftCollab.dto.AuthResponseDTO;
import com.siddarthavadavalasa.SwiftCollab.dto.RefreshTokenRequestDTO;
import com.siddarthavadavalasa.SwiftCollab.dto.UserLoginDTO;
import com.siddarthavadavalasa.SwiftCollab.dto.UserRegisterDTO;
import com.siddarthavadavalasa.SwiftCollab.dto.UserResponseDTO;
import com.siddarthavadavalasa.SwiftCollab.model.RefreshToken;
import com.siddarthavadavalasa.SwiftCollab.model.User;
import com.siddarthavadavalasa.SwiftCollab.repository.RefreshTokenRepository;
import com.siddarthavadavalasa.SwiftCollab.repository.UserRepository;
import org.springframework.security.core.Authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public UserController(
        UserRepository userRepository,
        UserService userService,
        RefreshTokenRepository refreshTokenRepository,
        RefreshTokenService refreshTokenService,
        JwtService jwtService
) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.refreshTokenRepository = refreshTokenRepository;
    this.refreshTokenService = refreshTokenService;
    this.jwtService = jwtService;
}


    // test endpoint
    @GetMapping("/test")
    public String test() {
        return "User API is working ✅";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserLoginDTO dto) {
        return ResponseEntity.ok(userService.loginUser(dto));
    }



    // register endpoint
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO dto) {
        System.out.println("Received DTO: " + dto); // log incoming object
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    @PostMapping("/refresh")
public ResponseEntity<AuthResponseDTO> refreshToken(
        @RequestBody RefreshTokenRequestDTO request
) {
    RefreshToken refreshToken = refreshTokenRepository
            .findByToken(request.getRefreshToken())
            .map(refreshTokenService::verifyExpiration)
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

    User user = refreshToken.getUser();
    String newAccessToken = jwtService.generateToken(user.getUsername());

    return ResponseEntity.ok(
        new AuthResponseDTO(newAccessToken, refreshToken.getToken())
    );
}
    @PostMapping("/logout")
public ResponseEntity<String> logout(Authentication authentication) {
    User user = userRepository
            .findByUsername(authentication.getName())
            .orElseThrow();

    refreshTokenRepository.deleteByUser(user);
    return ResponseEntity.ok("Logged out successfully");
}


    // get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
