package com.mall.admin.controller;

import com.mall.admin.pojo.*;
import com.mall.admin.service.OrderInfoService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
     * 处理对/order_list的GET请求
     */
    @GetMapping("/order_list")
    public String getOrderList(Model model,
                               HttpServletRequest request) {
        //页码
        String pageNumStr = request.getParameter("pageNum");
        int pageNum = 0;
        if (StringUtils.isNotBlank(pageNumStr)) {
            try {
                pageNum = Integer.parseInt(pageNumStr);
            } catch (Exception ignored) {
            }
        }
        //订单编号
        String idStr = request.getParameter("id");
        //客户名称
        String clientnameStr = request.getParameter("clientname");
        //订单状态
        String statusStr = request.getParameter("status");
        //查询时间起点
        String fromStr = request.getParameter("from");
        //查询时间终点
        String toStr = request.getParameter("to");

        //实现条件查询，组合查询
        Specification<OrderInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
            //用列表装载断言对象
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(statusStr)) {
                try {
                    Long id = Long.parseLong(idStr);
                    //精确查询，equal
                    Predicate predicate = criteriaBuilder.equal(root.get("id"), id);
                    predicates.add(predicate);
                } catch (Exception ignored) {
                }
            }
            if (StringUtils.isNotBlank(clientnameStr)) {
                //精确查询，equal
                Predicate predicate = criteriaBuilder.equal(root.get("userInfo").get("username"), clientnameStr);
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
            if (StringUtils.isNotBlank(fromStr)) {
                //验证输入是否是合法的日期格式
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    format.setLenient(false);
                    format.parse(fromStr);
                    //大于等于from
                    Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("orderTime").as(String.class), fromStr);
                    predicates.add(predicate);
                } catch (Exception ignored) {
                }
            }
            if (StringUtils.isNotBlank(toStr)) {
                //验证输入是否是合法的日期格式
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    format.setLenient(false);
                    format.parse(toStr);
                    //大于等于from
                    Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("orderTime").as(String.class), toStr);
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

        Page<OrderInfo> orders = orderInfoService.findOrderInfo(specification, pageNum);
        model.addAttribute("orders", orders);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("order_status", GlobalVariables.orderStatus);
        return "order_list";
    }

    /**
     * 处理对/order_list的POST请求
     */
    @PostMapping("/order_list")
    public String postOrderList(HttpServletRequest request) {
        //获取要下架的商品id
        String[] cancelIds = request.getParameterValues("cancelIds");
        if (cancelIds == null || cancelIds.length == 0) {
            return "redirect:/admin/order_admin/order_list?error=no_order_selected";
        }
        //获取操作员，日志记录用
        AdminInfo adminInfo = (AdminInfo) request.getSession().getAttribute("adminInfo");
        //逐条取消
        for (String idStr : cancelIds) {
            Long id = Long.parseLong(idStr);
            orderInfoService.modifyStatus(id, 7);
            OrderInfo orderInfo = orderInfoService.getOrderInfoById(id);
            log.info(adminInfo.getUsername() + " 取消了订单 " + orderInfo);
        }
        return "redirect:/admin/order_admin/order_list?cancel_count=" + cancelIds.length;
    }

    @GetMapping("/order_detail")
    public String getOrderDetail(Model model, HttpServletRequest request) {
        Long orderId = Long.parseLong(request.getParameter("order_id"));
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
        List<OrderDetail> orderDetails = orderInfo.getOrderDetails();
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orderTotalPrice", orderInfo.getOrderPrice());
        return "order_details";
    }
}
