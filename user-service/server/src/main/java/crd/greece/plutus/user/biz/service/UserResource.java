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

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Maps;
import crd.greece.plutus.user.biz.dao.UserDao;
import crd.greece.plutus.user.client.api.UserClient;
import crd.greece.plutus.user.client.common.ResponseJson;
import crd.greece.plutus.user.client.dto.UserDTO;
import crd.greece.plutus.user.common.Constants;
import crd.greece.plutus.user.domain.UserDomain;
import crd.greece.plutus.user.support.JWTParam;
import crd.greece.plutus.user.utils.JavaWebTokenUtils;
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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    private static final Integer MAXIMUM_SIZE = 5;
    private static final Integer INITIAL_CAPACITY = 3;

    private final Cache<String,Object> cache = buildCache(MAXIMUM_SIZE, INITIAL_CAPACITY);

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
        ResponseJson json = new ResponseJson();

        String email = (String) cache.getIfPresent(activeCode);
        if (!Strings.isNullOrEmpty(email)){
            UserDomain domain = userDao.selectByEmail(email);

            domain.setEnable(Boolean.TRUE);

            userDao.update(domain);

            json.setMsg("账户激活成功");
        }

        return json;
    }

    @Override
    public ResponseJson login(@RequestBody UserDTO userDTO) {
        ResponseJson json = new ResponseJson(Boolean.TRUE);
        //登录密码
        String password = DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes());

        UserDomain userDomain = userDao.selectByEmail(userDTO.getEmail());
        if (null == userDomain){
            json.setSuccess(Boolean.FALSE);
            json.setMsg("账号不存在");
            return json;
        } else if (!userDomain.getEnable()){
            json.setSuccess(Boolean.FALSE);
            json.setMsg("账号未激活，请先激活账号");
            return json;
        } else if (!password.equals(userDomain.getPassword())){
            json.setSuccess(Boolean.FALSE);
            json.setMsg("账号或密码不正确");
            return json;
        }

        //创建token
        Map<String, Object> resultMap = onLogin(userDomain);

        json.setMsg("登录成功");
        json.setAttributes(resultMap);

        return json;
    }

    /**
     * 
     * @param ud
     * @return
     */
    private Map<String, Object> onLogin(UserDomain ud){
        Map<String, Object> result = Maps.newHashMap();

        String jwtId = UUID.randomUUID().toString();

        Map<String, Object> claims = Maps.newHashMap();
        claims.put("email", ud.getEmail());
        claims.put("name", ud.getName());

        JWTParam param = JWTParam.builder()
                .id(jwtId)
                .issuer(Constants.ISSUER)
                .subject(Constants.SUBJECT)
                .ttlMillis(Constants.TTLMILLIS)
                .key(Constants.SECRET_KEY)
                .claims(claims)
                .build();

        String token = JavaWebTokenUtils.createJWT(param);
        cache.put(jwtId, token);

        result.put("tokenName", Constants.TOKEN_COOKIE);
        result.put("tokenValue", token);
        result.put("expireTime", Constants.TTLMILLIS);

        return result;
    }

    /**
     * 发送激活邮件
     * @param toMail
     * @return
     */
    private String sendActiveMail(String toMail) throws Exception {
        String from = fromEmail + "@" + host.substring(host.indexOf(".") + 1);

        //缓存验证码
        String random = RandomStringUtils.random(32, Constants.SYSTEM_RANDOM_STRING);
        cache.put(random, toMail);

        //用于封装邮件信息的实例
        SimpleMailMessage smm = new SimpleMailMessage();
        //由谁来发送邮件
        smm.setFrom(from);
        //邮件主题
        smm.setSubject("Plutus账号激活邮件");
        //邮件内容
        smm.setText(initValidateUrl(random));
        //接受邮件
        smm.setTo(toMail);
        try {
            jms.send(smm);
        } catch (Exception e) {
            throw new Exception("Plutus账号激活邮件发送失败");
        }

        return "注册成功！请尽快登录[" + toMail + "]激活您的账号！";
    }

    private String initValidateUrl(String random){
        StringBuffer sb = new StringBuffer("http://www.plutus.crd/")
                .append("user/active/").append(random);
        return sb.toString();
    }

    /**
     *
     * @param maximumSize
     * @param initialCapacity
     * @return
     */
    private Cache<String, Object> buildCache(int maximumSize, int initialCapacity){
        return CacheBuilder.newBuilder().maximumSize(maximumSize)
                .expireAfterWrite(3, TimeUnit.MINUTES)
//                .expireAfterWrite(30, TimeUnit.MINUTES)
                .initialCapacity(initialCapacity)
                .removalListener((RemovalNotification<String, Object> rn) -> deleteUser( rn))
                .build();
    }

    /**
     *
     * @param rn
     */
    private void deleteUser(RemovalNotification<String, Object> rn){
        String email = (String) rn.getValue();
        userDao.delete(email);
    }
}
