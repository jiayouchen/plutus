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
package crd.greece.plutus.user.client.api;

import crd.greece.plutus.user.client.common.ResponseJson;
import crd.greece.plutus.user.client.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/13 11:08
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@Api(value = "User Api", description = "用户 API", protocols = "application/json")
public interface UserClient {

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParam(name = "userDTO", value = "用户对象", paramType = "body", dataType = "UserDTO", required = true)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ResponseJson register(@RequestBody UserDTO userDTO);

    @ApiOperation(value = "账户激活", notes = "账户激活")
    @ApiImplicitParam(name = "activeCode", value = "激活码", paramType = "path", dataType = "string", required = true)
    @RequestMapping(value = "/active/{activeCode}", method = RequestMethod.GET)
    ResponseJson active(@PathVariable("activeCode") String activeCode);

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParam(name = "userDTO", value = "用户对象", paramType = "body", dataType = "UserDTO", required = true)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseJson login(@RequestBody UserDTO userDTO);

}
