package com.ch.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.product.entity.AttrGroupEntity;
import com.ch.mall.product.vo.AttrAttrGroupRelationVo;
import com.ch.mall.product.vo.AttrGroupWithAttrsRespVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 12:25:14
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    void deleteRelations(List<AttrAttrGroupRelationVo> vos);

    List<AttrGroupWithAttrsRespVo> selectAttrGroupWithAttrs(Long catelogId);
}

