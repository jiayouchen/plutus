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
package crd.greece.plutus.user.client;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/16 21:42
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
public interface AppServiceDefine {
    String APP_SERVICE_NAME = "user-service-${spring.profiles.active}";
//    String API_SERVER_PREFIX = "/server";
//    String API_WEBAPI_PREFIX = "/api";
}
