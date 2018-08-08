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
package crd.greece.plutus.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/8 12:32
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull
    @ApiModelProperty("用户名")
    private String userName;

    @NotNull
    @ApiModelProperty("密码")
    private String password;

    @NotNull
    @ApiModelProperty("邮箱")
    private String email;

    @NotNull
    @ApiModelProperty("手机")
    private String phone;

    @NotNull
    @ApiModelProperty("是否启用")
    private Boolean enable;

    @NotNull
    @ApiModelProperty("用户类型")
    private Integer type;

}
