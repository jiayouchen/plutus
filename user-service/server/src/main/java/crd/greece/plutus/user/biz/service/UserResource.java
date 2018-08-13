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
package crd.greece.plutus.user.biz.service;

import crd.greece.plutus.user.biz.dao.UserDao;
import crd.greece.plutus.user.client.api.UserClient;
import crd.greece.plutus.user.client.common.ResponseJson;
import crd.greece.plutus.user.client.dto.UserDTO;
import crd.greece.plutus.user.domain.UserDomain;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/13 11:14
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@Validated
@RestController
@Api(value = "User Api", description = "用户 API", protocols = "application/json")
public class UserResource implements UserClient {

    @Autowired
    private UserDao userDao;

    @Override
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParam(name = "userDTO", value = "用户对象", paramType = "body", dataType = "UserDTO", required = true)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseJson register(@RequestBody UserDTO userDTO){

        ResponseJson responseJson = new ResponseJson();
        try{
            UserDomain domain = new UserDomain();
            BeanUtils.copyProperties(userDTO, domain);
            //设置密码
            domain.setPassword(DigestUtils.md5DigestAsHex(domain.getPassword().getBytes()));
            domain.setEnable(Boolean.FALSE);

            userDao.insert(domain);
        }catch (Exception e){
            responseJson.setSuccess(Boolean.FALSE);
            responseJson.setMsg(e.getMessage());
        }

        return responseJson;
    }
}
