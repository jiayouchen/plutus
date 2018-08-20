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
package crd.greece.plutus.user.client.hystrix;

import crd.greece.plutus.user.client.api.UserClient;
import crd.greece.plutus.user.client.common.ResponseJson;
import crd.greece.plutus.user.client.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/16 21:49
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@Component
public class UserClientFallBack implements UserClient {
    @Override
    public ResponseJson register(@RequestBody UserDTO userDTO) {
        return null;
    }

    @Override
    public ResponseJson active(@PathVariable("activeCode") String activeCode) {
        return null;
    }
}
