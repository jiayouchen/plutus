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
package crd.greece.plutus.user.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/11 15:10
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@Data
@ApiModel(description = "用户对象")
public class UserDTO implements Serializable{

    private String id;

    /**
     * 用户名
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "用户名", required = true)
    private String name;

    /**
     * 密码
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    /**
     * 邮箱
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    /**
     * 手机
     */
    @NotNull
    @Size(max = 15)
    @ApiModelProperty(value = "手机", required = true)
    private String phone;


}
