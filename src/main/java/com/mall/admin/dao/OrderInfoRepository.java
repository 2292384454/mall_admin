package com.mall.admin.dao;

import com.mall.admin.pojo.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/28 17:30
 *
 * @author KevinHwang
 */
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
}
