package com.mall.admin.service.impl;

import com.mall.admin.dao.TypeRepository;
import com.mall.admin.pojo.Pager;
import com.mall.admin.pojo.Type;
import com.mall.admin.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/29 21:26
 *
 * @author KevinHwang
 */
@Service
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    private final Pager pager;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository, Pager pager) {
        this.typeRepository = typeRepository;
        this.pager = pager;
    }

    @Override
    public Page<Type> findType(int pageNum) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(0, pager.getPerPageRows(), Sort.by(orders));
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> findType() {
        return typeRepository.findAll();
    }

    @Override
    public void updateType(Type type) {
        //TODO: 待验证
        /*
          save方法：
          若db中这个id对应的字段不存在，则插入
          若db中这个id对应的字段存在，则更新
         */
        typeRepository.save(type);
    }
}
