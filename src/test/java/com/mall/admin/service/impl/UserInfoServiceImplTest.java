package com.mall.admin.service.impl;

import com.mall.admin.pojo.UserInfo;
import com.mall.admin.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * UserInfoServiceImpl测试.
 * <p>
 * 创建时间: 2021/5/28 19:28
 *
 * @author KevinHwang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceImplTest {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 测试FindUserInfo方法
     */
    @Test
    public void testFindUserInfo() {
        int pageNum = 0;
        Page<UserInfo> page = userInfoService.findUserInfo(pageNum);
        System.out.println("共" + page.getTotalElements() + "条记录");
        System.out.println("共" + page.getTotalPages() + "页");
        System.out.println("当前第" + pageNum + "页面");
        List<UserInfo> userInfos = page.toList();
        for (UserInfo userInfo : userInfos) {
            System.out.println("id:" + userInfo.getId() + "\tuserName:" + userInfo.getRealName());
        }
    }

    /**
     * 测试GetValidUser方法
     */
    @Test
    public void testGetValidUser() {
        List<UserInfo> validUsers = userInfoService.getValidUser();
        for (UserInfo userInfo : validUsers) {
            System.out.println("id:" + userInfo.getId() + "\tuserName:" + userInfo.getRealName());
        }
    }
}
