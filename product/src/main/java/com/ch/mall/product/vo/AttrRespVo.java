package com.ch.mall.product.vo;

import lombok.Data;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName AttrRespVo.java
 * @Description TODO
 * @createTime 2022年11月20日 15:37:00
 */
@Data
public class AttrRespVo extends AttrVo{
    //所属分类名字
    private String catelogName;
    //所属分组名字
    private String groupName;
    //category完整路径
    private Long[] catelogPath;
}
