package com.mall.admin.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/5/29 18:49
 *
 * @author KevinHwang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EncoderTset {
    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void TestEncoder() {
        String password = "admin123456";
        System.out.println("原始密码:" + password);
        System.out.println("加密后:" + encoder.encode(password));
    }
}
