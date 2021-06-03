package com.mall.admin.pojo;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 封装客户信息.
 * <p>
 * 创建时间: 2021/5/28 16:23
 *
 * @author KevinHwang
 */

@Data
@Entity
@Table(name = "user_info")//表明Order实体应该持久化到数据库中指定名字的表中
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "UserName name is required")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Password name is required")
    private String password;

    @NotBlank(message = "RealName name is required")
    @Column(name = "real_name")
    private String realName;

    @NotBlank(message = "Sex name is required")
    private String sex;

    @NotBlank(message = "Address name is required")
    private String address;

    @NotBlank(message = "Email name is required")
    private String email;

    @Column(name = "reg_date")
    private String regDate;

    @NotBlank(message = "UserName name is required")
    private int status;

    /**
     * 设置注册时间
     */
    @PrePersist
    void regDate() {
        this.regDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    @Override
    public String toString() {
        return username;
    }
}
