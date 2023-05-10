package org.charlie.ratelimiter;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 有了 RuleConfig 来存储限流规则，为什么还要 RateLimitRule 类呢？
 * 这是因为，限流过程中会频繁地查询接口对应的限流规则，为了尽可能地提高查询速度，
 * 需要将限流规则组织成一种支持按照 URL 快速查询的数据结构。
 * 考虑到 URL 的重复度比较高，且需要按照前缀来匹配，这里选择使用 Trie 树这种数据结构。
 * @author Charlie-6327
 * @date 2023/5/10
 */
@Data
@AllArgsConstructor
public class RateLimitRule {

    private RuleConfig ruleConfig;

    public RuleConfig.RateLimit getRateLimit(String appId, String url) {
        return null;
    }
}
