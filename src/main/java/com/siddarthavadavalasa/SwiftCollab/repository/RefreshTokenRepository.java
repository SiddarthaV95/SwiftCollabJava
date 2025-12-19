package com.siddarthavadavalasa.SwiftCollab.repository;

import com.siddarthavadavalasa.SwiftCollab.model.RefreshToken;
import com.siddarthavadavalasa.SwiftCollab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
