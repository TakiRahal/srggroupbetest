package com.takirahal.srfgroup.modules.user.services;

import com.takirahal.srfgroup.modules.user.dto.RefreshTokenDTO;
import com.takirahal.srfgroup.modules.user.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    /**
     * @param token
     * @return
     */
    Optional<RefreshToken> findByToken(String token);


    /**
     *
     * @param userId
     * @return
     */
    RefreshTokenDTO createRefreshToken(Long userId);

    /**
     *
     * @param token
     * @return
     */
    RefreshToken verifyExpiration(RefreshToken token);
}
