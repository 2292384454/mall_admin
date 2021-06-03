package com.mall.admin.security;

import com.mall.admin.pojo.AdminInfo;
import com.mall.admin.service.impl.AdminInfoServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security配置类.
 * <p>
 * 创建时间: 2021/5/28 22:08
 *
 * @author KevinHwang
 */
@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AdminInfoServiceImpl adminInfoService;

    @Autowired
    public SecurityConfig(AdminInfoServiceImpl adminInfoService) {
        this.adminInfoService = adminInfoService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * ROLE_ADMIN拥有所有的角色权限
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_PRODUCT_ADMIN \n " +
                "ROLE_ADMIN > ROLE_ORDER_ADMIN \n " +
                "ROLE_ADMIN > ROLE_USER_ADMIN");
        return hierarchy;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义用户认证
        auth.userDetailsService(adminInfoService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 只有具备管理员角色的用户才能访问/admin
                .antMatchers("/admin").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PRODUCT_ADMIN')" +
                "or hasRole('ROLE_ORDER_ADMIN') or hasRole('ROLE_USER_ADMIN')")
                // 只有具有ROLE_PRODUCT_ADMIN或其祖先角色才能访问的页面
                .antMatchers("/admin/product_admin/**").access(" hasRole('ROLE_PRODUCT_ADMIN')")
                // 只有具有ROLE_ORDER_ADMIN或其祖先角色才能访问的页面
                .antMatchers("/admin/order_admin/**").access("hasRole('ROLE_ORDER_ADMIN')")
                // 只有具有ROLE_USER_ADMIN或其祖先角色才能访问的页面
                .antMatchers("/admin/user_admin/**").access("hasRole('ROLE_USER_ADMIN')")
                // 无需认证的请求路径
                .antMatchers("/").access("permitAll()")

                .and()
                .formLogin()
                // 指定登录页视图
                .loginPage("/login")
                // 指定登录成功行为
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    AdminInfo user = (AdminInfo) authentication.getPrincipal();
                    // 登录成功日志记录
                    log.info("登录成功，{id:" + user.getId() + ",用户名:" + user.getUsername() + "}");
                    // 登录成功重定向到/admin
                    httpServletResponse.sendRedirect("/admin");
                })
                // 必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                .permitAll()

                .and()
                .logout()
                // 指定退出成功行为
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    AdminInfo user = (AdminInfo) authentication.getPrincipal();
                    // 退出成功日志记录
                    log.info("退出成功，{id:" + user.getId() + ",用户名:" + user.getUsername() + "}");
                    // 退出成功重定向到主页
                    httpServletResponse.sendRedirect("/");
                })

                .and()
                .csrf()

                .and()
                .headers()
                .frameOptions()
                .sameOrigin();
    }
}
