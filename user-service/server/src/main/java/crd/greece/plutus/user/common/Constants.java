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
package crd.greece.plutus.user.common;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/13 22:33
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
public class Constants {

    public static final String SYSTEM_RANDOM_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    //=================================认证相关=================================
    public static final String TOKEN_COOKIE = "token";

    public static final String ISSUER = "plutus-user-service";

    public static final String SUBJECT = "user-auth";

    public static final Integer TTLMILLIS = 30 * 60 * 1000;

    public static final String SECRET_KEY = "p@55w0rd";

}
