package com.mall.admin.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 辅助类，封装分页信息.
 * <p>
 * 创建时间: 2021/5/28 17:15
 *
 * @author KevinHwang
 */
@Component
@ConfigurationProperties(prefix = "com.ecpbm.dao.pojo")
@Data
public class Pager {
    /**
     * 每页显示的记录数
     */
    @Min(value = 5, message = "must be between 5 and 25")
    @Max(value = 25, message = "must be between 5 and 25")
    private int perPageRows = 10;
}
