package com.udtt.backend.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 로그아웃된 토큰을 블랙리스트로 관리.
 * 토큰 만료 시각 이후에는 어차피 검증 실패하므로 자동으로 무효화됨.
 *
 * ※ 운영 환경에서 다중 서버 구성 시 Redis로 교체 권장.
 */
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    // key: token, value: 만료 시각(ms)
    private final ConcurrentHashMap<String, Long> blacklist = new ConcurrentHashMap<>();

    public void addToBlacklist(String token, Date expiration) {
        blacklist.put(token, expiration.getTime());
        evictExpired();
    }

    public boolean isBlacklisted(String token) {
        Long expiryMs = blacklist.get(token);
        if (expiryMs == null) return false;
        if (System.currentTimeMillis() > expiryMs) {
            blacklist.remove(token);
            return false;
        }
        return true;
    }

    /** 만료된 항목 정리 */
    private void evictExpired() {
        long now = System.currentTimeMillis();
        blacklist.entrySet().removeIf(entry -> entry.getValue() < now);
    }
}
