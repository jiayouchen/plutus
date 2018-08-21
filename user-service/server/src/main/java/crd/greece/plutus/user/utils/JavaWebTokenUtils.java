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
package crd.greece.plutus.user.utils;

import crd.greece.plutus.user.support.JWTParam;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/8 17:59
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
public class JavaWebTokenUtils {

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    public static String createJWT(JWTParam param) {
        //指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey(param.getKey());
        //为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                .setIssuer(param.getIssuer())
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值
                //一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(param.getClaims())
                //设置jti(JWT ID)：是JWT的唯一标识，从而回避重放攻击。
                .setId(param.getId())
                .setIssuedAt(now)
                //subject代表这个JWT的主体，即它的所有人。
                .setSubject(param.getSubject())
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        if (param.getTtlMillis() >= 0) {
            long expMillis = nowMillis + param.getTtlMillis();
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Claims parseJWT(String jwt, String stringKey) {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey(stringKey);
        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(jwt).getBody();

        return claims;
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    private static SecretKey generalKey(String stringKey){
        //本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

}
