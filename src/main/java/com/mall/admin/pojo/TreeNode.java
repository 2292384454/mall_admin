package com.mall.admin.pojo;

import lombok.Data;

import java.util.List;

/**
 * 系统使用EasyUI提供的Tree控件来显示菜单，TreeNode类用于封装属性控件的结点信息.
 * <p>
 * 创建时间: 2021/5/28 17:25
 *
 * @author KevinHwang
 */
@Data
public class TreeNode {
    /**
     * 结点id
     */
    private int id;
    /**
     * 结点名称
     */
    private String text;
    /**
     * 父结点id
     */
    private int fid;
    /**
     * 包含的子结点
     */
    private List<TreeNode> childrens;
}
