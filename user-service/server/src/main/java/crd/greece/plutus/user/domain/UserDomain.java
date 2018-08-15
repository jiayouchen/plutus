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
package crd.greece.plutus.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/11 14:56
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain implements Serializable {

    private static final long serialVersionUID = 6326946714200475436L;

    /**
     * 主键
     */
    @NotNull
    private Long id;

    /**
     * 用户名
     */
    @NotNull
    private String name;

    /**
     * 密码
     */
    @NotNull
    private String password;

    /**
     * 邮箱
     */
    @NotNull
    private String email;

    /**
     * 手机
     */
    @NotNull
    private String phone;

    /**
     * 是否启用
     */
    @NotNull
    private Boolean enable;

}