package com.ch.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 12:25:14
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenusByIds(List<Long> asList);

    List<CategoryEntity> selectBrothersByParentCid(Long parentCid, Integer level);
}

