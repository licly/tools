package org.charlie.ratelimiter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Charlie-6327
 * @date 2023/5/10
 */
public class AnnotationRuleConfigSource implements RuleConfigSource {

    private Method method;

    private Class<?> cla;

    private RuleConfigParser ruleConfigParser;

    public AnnotationRuleConfigSource(Method method) {
        this.method = method;
    }

    @Override
    public RuleConfig load() {
        Annotation annotation = method.getAnnotation(null);
        if (annotation == null) {
            throw new IllegalArgumentException();
        }

        return ruleConfigParser.parse(annotation);
    }
}
