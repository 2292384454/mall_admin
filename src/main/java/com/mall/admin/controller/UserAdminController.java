package com.mall.admin.controller;

import com.mall.admin.pojo.AdminInfo;
import com.mall.admin.pojo.GlobalVariables;
import com.mall.admin.pojo.UserInfo;
import com.mall.admin.service.UserInfoService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/6/1 21:35
 *
 * @author KevinHwang
 */
@Controller
@RequestMapping("/admin/user_admin")
@Log4j2
public class UserAdminController {
    private final UserInfoService userInfoService;

    @Autowired
    public UserAdminController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * 处理对/user_list的GET请求
     */
    @GetMapping("/user_list")
    public String getUserList(Model model, HttpServletRequest request) {
        //从session获取当前操作人员，日志记录用
        AdminInfo adminInfo = (AdminInfo) request.getSession().getAttribute("adminInfo");

        //页码
        String pageNumStr = request.getParameter("pageNum");
        //用户状态
        String statusStr = request.getParameter("status");

        int pageNum = 0;
        if (StringUtils.isNotBlank(pageNumStr)) {
            try {
                pageNum = Integer.parseInt(pageNumStr);
            } catch (Exception ignored) {
            }
        }
        //实现条件查询，组合查询
        Specification<UserInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
            String keyword = request.getParameter("keyword");
            //用列表装载断言对象
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(keyword)) {
                Predicate predicate = criteriaBuilder.like(root.get("username").as(String.class), "%" + keyword + "%");
                predicates.add(predicate);
            }
            if (StringUtils.isNotBlank(statusStr)) {
                try {
                    int status = Integer.parseInt(statusStr);
                    //精确查询，equal
                    Predicate predicate = criteriaBuilder.equal(root.get("status").as(Integer.class), status);
                    predicates.add(predicate);
                } catch (Exception ignored) {
                }
            }
            //判断是否有断言，如果没有则返回空，不进行条件组合
            if (predicates.size() == 0) {
                return null;
            }
            //转换为数组，组合查询条件
            Predicate[] p = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(p));
        };
        //交给DAO处理查询任务
        Page<UserInfo> users = userInfoService.findByCondition(specification, pageNum);
        model.addAttribute("adminInfo", adminInfo);
        model.addAttribute("users", users);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("user_status", GlobalVariables.userStatus);
        return "user_list";
    }

    /**
     * 处理对/user_list的POST请求
     */
    @PostMapping("/user_list")
    public String postUserList(HttpServletRequest request) {
        //从session获取当前操作人员，日志记录用
        AdminInfo adminInfo = (AdminInfo) request.getSession().getAttribute("adminInfo");
        Long disableId, enableId;
        //尝试禁用用户，如果disableId为空或者不合法就不做操作
        try {
            disableId = Long.parseLong(request.getParameter("disableId"));
            userInfoService.modifyStatus(disableId, 0);
            log.info(adminInfo.getUsername() + " 禁用用户 {id:" + disableId + ",用户名:" + userInfoService.getUserInfoById(disableId).getUsername() + "}");
        } catch (Exception ignored) {
        }
        //尝试启用用户，如果enableId为空或者不合法就不做操作
        try {
            enableId = Long.parseLong(request.getParameter("enableId"));
            userInfoService.modifyStatus(enableId, 1);
            log.info(adminInfo.getUsername() + " 启用用户 {id:" + enableId + ",用户名:" + userInfoService.getUserInfoById(enableId).getUsername() + "}");
        } catch (Exception ignored) {
        }

        String url = "/admin/user_admin/user_list";
        if (request.getQueryString() != null && !"null".equals(request.getQueryString())) {
            url += "?" + request.getQueryString();
        }
        return "redirect:" + url;
    }
}
