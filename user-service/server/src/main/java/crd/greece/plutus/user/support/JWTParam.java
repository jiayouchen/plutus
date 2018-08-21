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
package crd.greece.plutus.user.support;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/8 18:27
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@Data
@Builder
public class JWTParam {

    private String id;
    private String issuer;
    private String subject;
    private String key;
    private Map<String,Object> claims;
    private long ttlMillis;

}
