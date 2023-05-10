package org.charlie.ratelimiter;

/**
 * @author Charlie-6327
 * @date 2023/5/10
 */
public interface RuleConfigSource {

    RuleConfig load();
}
