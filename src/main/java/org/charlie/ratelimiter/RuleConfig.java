package org.charlie.ratelimiter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 限流规则配置，对应限流配置三层结构
 * configs:
 * - appId: app-1
 *   limits:
 *   - api: /v1/user
 *     limit: 100
 *     unit：60
 *   - api: /v1/order
 *     limit: 50
 *
 * @author Charlie-6327
 * @date 2023/5/10
 */
@Data
public class RuleConfig {

    private List<AppRuleConfig> configs;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class AppRuleConfig {
        private String appId;
        private List<RateLimit> limits;
    }

    @Data
    @NoArgsConstructor
    public static class RateLimit {

        // 1s
        private static int DEFAULT_TIME_UNIT = 1;
        private String api;
        private int limit;
        private int unit = DEFAULT_TIME_UNIT;

        public RateLimit(String api, int limit) {
            this(api, limit, DEFAULT_TIME_UNIT);
        }

        public RateLimit(String api, int limit, int unit) {
            this.api = api;
            this.limit = limit;
            this.unit = unit;
        }

    }
}
