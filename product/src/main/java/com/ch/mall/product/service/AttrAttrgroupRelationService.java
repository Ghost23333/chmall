package com.ch.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.product.entity.AttrAttrgroupRelationEntity;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 12:25:14
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

