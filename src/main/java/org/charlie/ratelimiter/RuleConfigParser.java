package org.charlie.ratelimiter;

import java.io.InputStream;
import java.lang.annotation.Annotation;

/**
 * @author Charlie-6327
 * @date 2023/5/10
 */
public interface RuleConfigParser {

    default RuleConfig parse(String configUrl) {
        return null;
    }

    default RuleConfig parse(InputStream is) {
        return null;
    }

    default RuleConfig parse(Annotation annotation) {
        return null;
    }
}
