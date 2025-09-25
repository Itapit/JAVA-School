package com.example.jwt_basics1.config;

import com.example.jwt_basics1.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutSuccessHandler {
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtUtil jwtUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header != null && header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            String token = header.substring(JwtProperties.TOKEN_PREFIX.length());
            long exp = jwtUtil.extractExpiration(token).getTime();
            tokenBlacklistService.blacklistToken(token, exp);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
