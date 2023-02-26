package com.ch.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.product.entity.AttrEntity;
import com.ch.mall.product.entity.ProductAttrValueEntity;
import com.ch.mall.product.vo.AttrRespVo;
import com.ch.mall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author chenhong
 * @email chenhong@
 * gmail.com
 * @date 2022-11-05 12:25:14
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrVo(AttrVo attrvo);

    PageUtils queryAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrRespVo getAttrRespInfo(Long attrId);

    void updateAttrVo(AttrVo attrvo);

    List<AttrEntity> queryRelationAttrsByGroupId(Long attrGroupId);

    PageUtils queryNotRelationAttrsPage(Map<String, Object> params, Long attrGroupId);

    List<Long> selectSearchAttrs(List<Long> attrIds);
}

