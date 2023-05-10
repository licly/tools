package org.charlie.ratelimiter;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Charlie-6327
 * @date 2023/5/10
 */
@Slf4j
public class RateLimiter {

    private Map<String, FixedTimeRateLimitAlg> counters = new ConcurrentHashMap<>();

    private RateLimitRule rule;

    public RateLimiter() {
        init();
    }

    /**
     * 读取限流规则
     */
    private void init() {
        RuleConfig ruleConfig = null;
        try (InputStream is = this.getClass().getResourceAsStream("classpath:rate_limiter-rule.yml")) {
            if (is == null) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(is, RuleConfig.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        rule = new RateLimitRule(ruleConfig);
    }

    public boolean limit(String appId, String url) {
        RuleConfig.RateLimit rateLimit = rule.getRateLimit(appId, url);
        if (rateLimit == null) {
            return true;
        }

        String counterKey = appId + ":" + rateLimit.getApi();
        FixedTimeRateLimitAlg rateLimitAlg = counters.get(counterKey);

        if (rateLimitAlg != null) {
            rateLimitAlg = new FixedTimeRateLimitAlg(rateLimit.getLimit());
        }

        return rateLimitAlg.tryAcquire();
    }

}
