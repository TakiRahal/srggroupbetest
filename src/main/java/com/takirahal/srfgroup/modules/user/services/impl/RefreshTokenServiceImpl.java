package com.takirahal.srfgroup.modules.user.services.impl;

import com.takirahal.srfgroup.modules.user.dto.RefreshTokenDTO;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.entities.RefreshToken;
import com.takirahal.srfgroup.modules.user.exceptioins.TokenRefreshException;
import com.takirahal.srfgroup.modules.user.mapper.RefreshTokenMapper;
import com.takirahal.srfgroup.modules.user.repositories.RefreshTokenRepository;
import com.takirahal.srfgroup.modules.user.services.RefreshTokenService;
import com.takirahal.srfgroup.modules.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    RefreshTokenMapper refreshTokenMapper;

    @Autowired
    UserService userService;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenDTO createRefreshToken(Long userId) {

        RefreshTokenDTO refreshToken = new RefreshTokenDTO();
        UserDTO user = userService.findById(userId);

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        RefreshToken result = refreshTokenRepository.save(refreshTokenMapper.toEntity(refreshToken));
        return refreshTokenMapper.toDto(result);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken()+ " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
