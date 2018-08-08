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
package crd.greece.plutus.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author chenjiayou022
 * @description TODO
 * @date 2018/8/8 10:50
 * @slogon 站在巨人的肩膀上
 * @since 1.0.0
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("system/login");
    }

}
