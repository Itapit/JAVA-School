package com.example.jwt_basics1.service;

import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {
    // Map<Token, ExpirationTimeMillis>
    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    // Add token to blacklist with its expiration time
    public void blacklistToken(String token, long expirationTimeMillis) {
        cleanup();
        blacklist.put(token, expirationTimeMillis);
    }

    // Check if token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        cleanup();
        return blacklist.containsKey(token);
    }

    // Remove expired tokens from blacklist
    private void cleanup() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, Long>> it = blacklist.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            if (entry.getValue() < now) {
                it.remove();
            }
        }
    }
}
