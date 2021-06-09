package com.mall.admin.service.impl;

import com.mall.admin.dao.UserInfoRepository;
import com.mall.admin.pojo.Pager;
import com.mall.admin.pojo.UserInfo;
import com.mall.admin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户信息服务实现类.
 * <p>
 * 创建时间: 2021/5/28 18:47
 *
 * @author KevinHwang
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final Pager pager;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, Pager pager) {
        this.userInfoRepository = userInfoRepository;
        this.pager = pager;
    }

    @Override
    public List<UserInfo> getValidUser() {
        return userInfoRepository.getValidUser();
    }

    @Override
    public UserInfo getUserInfoById(Long id) {
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findById(id);
        if (!optionalUserInfo.isPresent()) {
            throw new UsernameNotFoundException("User' " + id + "' not found");
        }
        return optionalUserInfo.orElse(null);
    }

    @Override
    public Page<UserInfo> findUserInfo(int pageNum) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(pageNum, pager.getPerPageRows(), Sort.by(orders));
        return userInfoRepository.findAll(pageable);
    }

    @Override
    public Page<UserInfo> findByCondition(Specification<UserInfo> specification, int pageNum) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(pageNum, pager.getPerPageRows(), Sort.by(orders));
        return userInfoRepository.findAll(specification, pageable);
    }

    @Override
    public Long count() {
        return userInfoRepository.count();
    }

    @Override
    @Transactional
    public void modifyStatus(Long id, int flag) {
        userInfoRepository.modifyStatus(id, flag);
    }
}
