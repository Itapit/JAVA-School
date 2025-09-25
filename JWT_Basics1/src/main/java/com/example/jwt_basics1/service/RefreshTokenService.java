package com.example.jwt_basics1.service;

import com.example.jwt_basics1.config.JwtUtil;
import com.example.jwt_basics1.dto.AuthenticationResponse;
import com.example.jwt_basics1.dto.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class RefreshTokenService {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenBlacklistService tokenBlacklistService;
    // Map<refreshToken, ipAddress>
    private final Map<String, String> refreshTokenIpMap = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private HttpServletRequest request;

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String username = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // בדיקת blacklist
        if (tokenBlacklistService.isTokenBlacklisted(refreshToken)) {
            throw new RuntimeException("Refresh token is blacklisted");
        }

        // בדיקת IP
        String requestIp = request != null ? request.getRemoteAddr() : null;
        String originalIp = refreshTokenIpMap.get(refreshToken);
        if (originalIp != null && requestIp != null && !originalIp.equals(requestIp)) {
            throw new RuntimeException("IP address mismatch for refresh token");
        }

        // validate refresh token
        if (!jwtUtil.validateToken(refreshToken, userDetails)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // הוספה ל-blacklist
        long exp = jwtUtil.extractExpiration(refreshToken).getTime();
        tokenBlacklistService.blacklistToken(refreshToken, exp);

        // יצירת טוקנים חדשים
        String newAccessToken = jwtUtil.generateToken(null, userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

        // שמירת IP חדש
        if (requestIp != null) {
            refreshTokenIpMap.put(newRefreshToken, requestIp);
        }

        return new AuthenticationResponse(newAccessToken, newRefreshToken);
    }
}
