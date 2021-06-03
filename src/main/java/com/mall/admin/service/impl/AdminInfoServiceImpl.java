package com.mall.admin.service.impl;

import com.mall.admin.dao.AdminInfoRepository;
import com.mall.admin.pojo.AdminInfo;
import com.mall.admin.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 实现AdminInfoService和UserDetailService接口，提供Admin用户查询服务和登录服务.
 * <p>
 * 创建时间: 2021/5/28 21:51
 *
 * @author KevinHwang
 */
@Service
public class AdminInfoServiceImpl implements AdminInfoService, UserDetailsService {
    private final AdminInfoRepository adminInfoRepository;

    @Autowired
    public AdminInfoServiceImpl(AdminInfoRepository adminInfoRepository) {
        this.adminInfoRepository = adminInfoRepository;
    }

    @Override
    public AdminInfo findByUserName(String username) {
        Optional<AdminInfo> adminInfoOptional = adminInfoRepository.findByUsername(username);
        if (!adminInfoOptional.isPresent()) {
            throw new UsernameNotFoundException("Username' " + username + "' not found");
        }
        return adminInfoOptional.get();
    }

    @Override
    public AdminInfo findById(Long id) {
        Optional<AdminInfo> adminInfoOptional = adminInfoRepository.findById(id);
        if (!adminInfoOptional.isPresent()) {
            throw new UsernameNotFoundException("Admin' " + id + "' not found");
        }
        return adminInfoOptional.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库获取用户信息
        return findByUserName(username);
    }
}
