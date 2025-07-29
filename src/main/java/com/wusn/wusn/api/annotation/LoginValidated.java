package com.wusn.wusn.api.annotation;

import java.lang.annotation.*;

/**
 * 登录验证注解
 *
 * @author wusn
 * @since 1.0.0.a
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LoginValidated {
}
