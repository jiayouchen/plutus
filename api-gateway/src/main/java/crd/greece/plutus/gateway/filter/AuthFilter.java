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
package crd.greece.plutus.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/11 21:22
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
public class AuthFilter extends ZuulFilter {

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static final UrlPathHelper urlPathHelper = new UrlPathHelper();

    private final String[] excludeUrlPattern = new String[]{
        "/user/register", "/user/login"
    };

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        String lookPath = urlPathHelper.getLookupPathForRequest(context.getRequest());

        // ORDER-1 优先处理需要排除的URL地址
        for (String exPattern : excludeUrlPattern) {
            if (antPathMatcher.match(exPattern, lookPath)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 1. 验证是否包含 Token
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        Cookie token = WebUtils.getCookie(request, "token");
        System.out.println(token);


        // 2. 验证Token的合法性
        String tokenString = request.getHeader("token");
        System.out.println(tokenString);

        HttpServletResponse response = context.getResponse();
        Cookie cookie = new Cookie("token", "token_test");
        cookie.setPath("/");
        cookie.setHttpOnly(false);

        response.addCookie(cookie);
        return null;
    }
}
