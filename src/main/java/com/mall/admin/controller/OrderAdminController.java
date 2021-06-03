package com.mall.admin.controller;

import com.mall.admin.pojo.AdminInfo;
import com.mall.admin.pojo.OrderInfo;
import com.mall.admin.service.OrderInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/6/1 21:26
 *
 * @author KevinHwang
 */
@Controller
@RequestMapping("/admin/order_admin")
@Log4j2
public class OrderAdminController {
    private final OrderInfoService orderInfoService;

    @Autowired
    public OrderAdminController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    /**
     * 处理对/create_order的请求
     */
    @GetMapping("/create_order")
    public String getCreateOrder() {
        return "order_list";
    }

    /**
     * 处理对/order_list的请求
     */
    @RequestMapping("/order_list")
    public String getOrderList( Model model,
                               HttpServletRequest request) {
        int pageNum = 0;
        try {
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        } catch (Exception ignored) {
        }
        Page<OrderInfo> orders = orderInfoService.findOrderInfo(pageNum);
        model.addAttribute("orders", orders);
        model.addAttribute("pageNum", pageNum);
        return "order_list";
    }
}
