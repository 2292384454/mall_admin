package com.mall.admin.repository;

import com.mall.admin.pojo.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/28 17:29
 *
 * @author KevinHwang
 */
public interface AdminInfoRepository extends JpaRepository<AdminInfo, Long> {
    Optional<AdminInfo> findByUsername(String username);
}
