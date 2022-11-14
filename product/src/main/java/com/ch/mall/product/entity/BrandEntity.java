package com.ch.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.ch.common.valid.AddGroup;
import com.ch.common.valid.ListValue;
import com.ch.common.valid.UpdateGroup;
import com.ch.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.aspectj.bridge.MessageWriter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 12:25:14
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    @NotNull(message = "修改必须指定id", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id", groups = {AddGroup.class})
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;


    /**
     * 品牌logo地址
     */
    @NotEmpty(groups = {AddGroup.class})
    @URL(message = "logo地址不合法",groups = {AddGroup.class, UpdateGroup.class})
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */

    @NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
    @ListValue(values = {0,1},groups = {AddGroup.class, UpdateStatusGroup.class})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotEmpty(groups={AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$",groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(groups={AddGroup.class})
    @Min(value = 0, message = "排序字段为非负数",groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;

}
