package com.mall.admin.dao;

import com.mall.admin.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/28 17:35
 *
 * @author KevinHwang
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
