package com.example.auth_service.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisTokenService {
    private final StringRedisTemplate redisTemplate;

    public void saveAccessToken(Long userId, String jti, Duration expiry) {

        redisTemplate.opsForValue().set("access:" + userId, jti, expiry);
    }

    public String getCurrentJti(Long userId) {
        return redisTemplate.opsForValue().get("access:" + userId);
    }
}
