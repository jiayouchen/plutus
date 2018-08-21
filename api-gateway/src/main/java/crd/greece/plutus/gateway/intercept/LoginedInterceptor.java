/*
 * 四川生学教育科技有限公司
 * Copyright (c) 2015-2025 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package crd.greece.plutus.gateway.intercept;

import com.google.common.base.Strings;
import crd.greece.plutus.gateway.common.TokenContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/13 10:26
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
public class LoginedInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = TokenContext.getToken();

        if (Strings.isNullOrEmpty(token)) {
            Cookie cookie = WebUtils.getCookie(request, "token");
            token = cookie.getValue();
            if (!Strings.isNullOrEmpty(token)){
                //设置
                TokenContext.setToken(token);
            } else {
                response.sendRedirect("/login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
