package com.mall.admin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @OneToOne
    @JoinColumn(name = "pid")
    private ProductInfo productInfo;

    private int num;
}
