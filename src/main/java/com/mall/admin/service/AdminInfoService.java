package com.mall.admin.service;

import com.mall.admin.pojo.AdminInfo;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/28 21:48
 *
 * @author KevinHwang
 */
public interface AdminInfoService {
    /**
     * 根据用户名获取AdminInfo对象
     *
     * @param username 用户名
     * @return AdminInfo对象
     */
    AdminInfo findByUserName(String username);

    /**
     * 根据管理员id，获取管理员对象及关联的功能权限
     *
     * @param id 管理员id
     * @return 管理员对象及相关功能权限
     */
    AdminInfo findById(Long id);
}
