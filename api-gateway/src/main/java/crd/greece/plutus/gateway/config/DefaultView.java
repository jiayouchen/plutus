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
package crd.greece.plutus.gateway.config;

import crd.greece.plutus.gateway.intercept.LoginedInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/8 12:48
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@Configuration
public class DefaultView implements WebMvcConfigurer {

    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName( "forward:/login" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new LoginedInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(new String[]{
                        "/",
                        "/login",
                        "/error",
                        "/static/**"
                });
    }
}