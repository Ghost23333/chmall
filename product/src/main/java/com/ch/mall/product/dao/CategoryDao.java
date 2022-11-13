package com.ch.mall.product.dao;

import com.ch.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品三级分类
 * 
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 12:25:14
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
    public List<CategoryEntity>  selectBrothersByParentCid(@Param("parentCid") Long parentCid,@Param("level") Integer level);
}
