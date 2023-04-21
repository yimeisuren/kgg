package org.dml.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NodeRedisPrefixAnnotation {

    /**
     * 在Redis中默认为其添加上NODE前缀
     *
     * @return
     */
    String value() default "NODE";
}
