package com.mall.admin.service;

import com.mall.admin.pojo.Type;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/29 21:21
 *
 * @author KevinHwang
 */
public interface TypeService {
    /**
     * 分页获取所有商品类型
     *
     * @param pageNum
     */
    Page<Type> findType(int pageNum);

    /**
     * 不分页获取所有商品类型
     */
    List<Type> findType();

    /**
     * 更新商品类型
     */
    void addType(String typeName);
}
