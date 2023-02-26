package com.ch.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.product.entity.ProductAttrValueEntity;
import com.ch.mall.product.vo.SpuSaveVo;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 12:25:14
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttrValue(Long id, List<SpuSaveVo.BaseAttrs> baseAttrs);

    List<ProductAttrValueEntity> listForSpuAttrValue(Long spuId);

    void updateSpuAttrs(Long spuId, List<ProductAttrValueEntity> entities);
}

