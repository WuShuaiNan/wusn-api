package com.wusn.wusn.api.annotation;

import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.stack.bean.dto.ResponseData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.wusn.wusn.api.service.acckeeper.LoginResponseService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证。
 *
 * @author wusn
 * @since 1.0.0
 */
@Component
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class LoginValidatedAdvisor {

    private static Logger logger = LoggerFactory.getLogger(LoginValidatedAdvisor.class);

    @Autowired
    private LoginResponseService loginResponseService;

    @Value("${app.token.key}")
    private String tokenKey;

    @Pointcut("@annotation(com.wusn.wusn.api.annotation.LoginValidated)")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object[] params = pjp.getArgs();
            boolean isLogin = false;
            // 1.通过HttpServletRequest，获取token。通过token，查询该用户是否登陆
            for (Object param : params) {
                if (param instanceof HttpServletRequest) {
                    isLogin = loginResponseService.isLogin(new LongIdKey(Long.parseLong(((HttpServletRequest) param).getHeader(tokenKey))));
                }
            }
            // 2.如果未登录，则进行拦截，并进行提示
            if (!isLogin) {
                return FastJsonResponseData.of(
                        new ResponseData<>(null, new ResponseData.Meta(0, "用户未登录！"))
                );
            }

            // 3.如果已登录，则不拦截继续执行
            return pjp.proceed(params);
        } catch (Exception e) {
            logger.error("系统错误：");
            logger.error(e.toString());
            return FastJsonResponseData.of(
                    new ResponseData<>(null, new ResponseData.Meta(0, "系统错误：" + e.toString()))
            );
        }
    }

}
