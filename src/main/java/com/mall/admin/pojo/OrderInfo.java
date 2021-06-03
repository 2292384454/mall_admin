package com.mall.admin.pojo;

import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 封装订单信息.
 * <p>
 * 创建时间: 2021/5/28 16:59
 *
 * @author KevinHwang
 */

@Data
@Entity
@Table(name = "order_info")
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @Column(name = "order_time")
    private String orderTime;

    @Column(name = "order_price")
    private double orderPrice;

    @Transient
    private String orderTimeFrom;

    @OneToOne
    @JoinColumn(name = "uid")
    private UserInfo userInfo;

    @Transient
    private String orderTimeTo;


    /**
     * 设置订单创建时间
     */
    @PrePersist
    void orderTime() {
        this.orderTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
