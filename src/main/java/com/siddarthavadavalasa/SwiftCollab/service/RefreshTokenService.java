package com.siddarthavadavalasa.SwiftCollab.service;

import com.siddarthavadavalasa.SwiftCollab.model.RefreshToken;
import com.siddarthavadavalasa.SwiftCollab.model.User;
import com.siddarthavadavalasa.SwiftCollab.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // 7 days
    private final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60;

    public RefreshToken createRefreshToken(User user) {
        // remove old refresh tokens
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(REFRESH_TOKEN_VALIDITY))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }
}
