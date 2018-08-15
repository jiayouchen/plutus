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
import crd.greece.plutus.user.common.Constants;
import crd.greece.plutus.user.domain.UserDomain;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/13 11:14
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@Api(value = "User Api", description = "用户 API", protocols = "application/json")
public class UserResource implements UserClient {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JavaMailSender jms;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${plutus.self-regist-switch}")
    private Boolean selfRegistSwitch;

    @Override
    public ResponseJson register(@RequestBody UserDTO userDTO){

        ResponseJson responseJson = new ResponseJson();

        try{
            if (!selfRegistSwitch){
                responseJson.setSuccess(Boolean.FALSE);
                responseJson.setMsg("注册功能未开放，请联系管理员");
                return responseJson;
            }

            UserDomain domain = new UserDomain();
            BeanUtils.copyProperties(userDTO, domain);
            //设置密码
            domain.setPassword(DigestUtils.md5DigestAsHex(domain.getPassword().getBytes()));
            domain.setEnable(Boolean.FALSE);

            String msg = sendActiveMail(userDTO.getEmail());
            responseJson.setMsg(msg);

            userDao.insert(domain);
        }catch (Exception e){
            responseJson.setSuccess(Boolean.FALSE);
            responseJson.setMsg(e.getMessage());
        }

        return responseJson;
    }

    @Override
    public ResponseJson active(@PathVariable("activeCode") String activeCode) {

        return null;
    }

    /**
     * 发送激活邮件
     * @param toMail
     * @return
     */
    private String sendActiveMail(String toMail) throws Exception {
        String from = fromEmail + "@" + host.substring(host.indexOf(".") + 1);
        //用于封装邮件信息的实例
        SimpleMailMessage smm = new SimpleMailMessage();
        //由谁来发送邮件
        smm.setFrom(from);
        //邮件主题
        smm.setSubject("Plutus账号激活邮件");
        //邮件内容
        smm.setText(genValidateUrl());
        //接受邮件
        smm.setTo(toMail);
        try {
            jms.send(smm);
        } catch (Exception e) {
            throw new Exception("Plutus账号激活邮件发送失败");
        }

        return "注册成功！请尽快登录[" + toMail + "]激活您的账号！";
    }

    private String genValidateUrl(){
        String random = RandomStringUtils.random(32, Constants.SYSTEM_RANDOM_STRING);
        StringBuffer sb = new StringBuffer("http://www.plutus.crd/")
                .append("user/active/").append(random);
        return sb.toString();
    }
}
