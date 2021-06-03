package com.mall.admin.controller;

import com.mall.admin.pojo.AdminInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/31 14:29
 *
 * @author KevinHwang
 */
@Controller
@RequestMapping("/admin")
@Log4j2
public class AdminController {
    /**
     * 处理对/和/*的请求，并将登录用户adminInfo的信息存储到session中
     */
    @RequestMapping(value = {"", "/*"})
    public String requestAdmin(@AuthenticationPrincipal AdminInfo adminInfo, Model model, HttpSession session) {
        model.addAttribute("adminInfo", adminInfo);
        session.setAttribute("adminInfo", adminInfo);
        return "admin";
    }
}
