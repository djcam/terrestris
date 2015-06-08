package com.terrestris.map.config;

/**
 * Created by dcampbell2 on 3/9/2015.
 */

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Layout {
    String value() default "";
}
