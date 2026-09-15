package com.example.api_gateway.Service;

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

//              USER 1
//                │
//        ┌───────┴────────┐
//        ↓                ↓
//      Login #1          Login #2
//        ↓                ↓
//      Token A             Token B
//      jti=AAA             jti=BBB
//        │                │
//        └───────┬────────┘
//                ↓
//              Redis
//          access:1 = BBB
//                ↑
//          only latest JTI

// JTI :- means JWT ID

//Instead of storing/reusing the access token, we store the latest access token's jti in Redis and use it
// to validate whether the incoming token is still the current one.
