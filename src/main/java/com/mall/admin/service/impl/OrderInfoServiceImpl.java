package com.mall.admin.service.impl;

import com.mall.admin.dao.OrderDetailRepository;
import com.mall.admin.dao.OrderInfoRepository;
import com.mall.admin.pojo.OrderDetail;
import com.mall.admin.pojo.OrderInfo;
import com.mall.admin.pojo.Pager;
import com.mall.admin.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/29 21:54
 *
 * @author KevinHwang
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    private final OrderInfoRepository orderInfoRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final Pager pager;

    @Autowired
    public OrderInfoServiceImpl(OrderInfoRepository orderInfoRepository, OrderDetailRepository orderDetailRepository, Pager pager) {
        this.orderInfoRepository = orderInfoRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.pager = pager;
    }

    @Override
    public Page<OrderInfo> findOrderInfo(int pageNum) {
        Pageable pageable = PageRequest.of(0, pager.getPerPageRows());
        return orderInfoRepository.findAll(pageable);
    }

    @Override
    public Long count() {
        return orderInfoRepository.count();
    }

    @Override
    public Long addOrderInfo(OrderInfo oi) {
        return orderInfoRepository.save(oi).getId();
    }

    @Override
    public Long addOrderDetailInfo(OrderDetail od) {
        return orderDetailRepository.save(od).getId();
    }

    @Override
    public OrderInfo getOrderInfoById(Long id) {
        return orderInfoRepository.findById(id).orElse(null);
    }

    @Override
    public OrderDetail getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOrder(Long id) {
        orderInfoRepository.deleteById(id);
        orderDetailRepository.deleteById(id);
    }
}
