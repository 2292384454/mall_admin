package com.mall.admin.pojo;

import lombok.Data;

/**
 * 封装商品查询条件.
 * <p>
 * 创建时间: 2021/5/28 17:21
 *
 * @author KevinHwang
 */
@Data
public class SearchProductInfo {
    /**
     * 商品id
     */
    private Long id;

    /**
     * 商品编号
     */
    private String code;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品品牌
     */
    private String brand;

    private double priceFrom;

    private double priceTo;

    /**
     * 商品种类id
     */
    private int tid;
}
