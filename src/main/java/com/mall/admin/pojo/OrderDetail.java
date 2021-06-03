package com.mall.admin.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 封装订单明细信息.
 * <p>
 * 创建时间: 2021/5/28 17:11
 *
 * @author KevinHwang
 */
@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long oid;

    private Long pid;

    private int num;

    /**
     * 获取订单信息
     */
    public OrderInfo getOi() {
        return null;
    }

    /**
     * 获取产品信息
     */
    public ProductInfo getPi() {
        return null;
    }

    /**
     * 获取产品价格
     */
    public double getPrice() {
        return 0.0;
    }

    /**
     * 获取订单总价
     */
    public double getTotalPrice() {
        return 0.0;
    }
}
