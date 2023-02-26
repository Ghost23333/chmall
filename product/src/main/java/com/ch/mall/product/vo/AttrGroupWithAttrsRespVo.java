package com.ch.mall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ch.mall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName AttrGroupWithAttrs.java
 * @Description TODO
 * @createTime 2022年11月22日 19:15:00
 */
@Data
public class AttrGroupWithAttrsRespVo  {
    /**
     * 分组id
     */
    @TableId
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    /**
     * 分组下属性的集合
     */
    private List<AttrEntity> attrs;
}
