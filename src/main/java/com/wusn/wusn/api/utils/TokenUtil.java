package com.wusn.wusn.api.utils;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * Token处理器。
 *
 * @author wusn
 * @since 1.0.0
 */
public class TokenUtil {

    @Value("${app.token.key}")
    private String tokenKey;

    public long getTokenId(HttpServletRequest httpServletRequest) throws ServiceException {
        try {
            return Long.parseLong(httpServletRequest.getHeader(tokenKey));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
