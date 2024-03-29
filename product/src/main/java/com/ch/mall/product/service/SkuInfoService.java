package com.ch.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.product.entity.SkuInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 12:25:14
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);
}

