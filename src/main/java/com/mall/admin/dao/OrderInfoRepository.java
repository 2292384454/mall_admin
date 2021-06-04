package com.mall.admin.dao;

import com.mall.admin.pojo.OrderInfo;
import com.mall.admin.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/28 17:30
 *
 * @author KevinHwang
 */
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long>, JpaSpecificationExecutor<OrderInfo> {

    /**
     * 更新订单状态
     */
    @Modifying
    @Query("UPDATE OrderInfo o set o.status=?2 WHERE o.id=?1")
    void modifyStatus(Long id, int flag);
}
