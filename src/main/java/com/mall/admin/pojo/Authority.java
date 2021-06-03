package com.mall.admin.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 封装系统功能信息.
 * <p>
 * 创建时间: 2021/5/28 16:36
 *
 * @author KevinHwang
 */
@Data
@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name is required")
    private String role;

    @NotBlank(message = "Parent Id is required")
    @Column(name = "parent_id")
    private int parentId;

    @Column(name = "is_leaf")
    private boolean isLeaf;

    @Override
    public String toString() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof SimpleGrantedAuthority && this.getAuthority().equals(((SimpleGrantedAuthority) obj).getAuthority());
        }
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }

    /**
     * 重写compareTo方法，按照id排序
     *
     * @param arg0 略
     * @return 略
     */
    public int compareTo(Authority arg0) {
        return Integer.compare(this.getId(), arg0.getId());
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
