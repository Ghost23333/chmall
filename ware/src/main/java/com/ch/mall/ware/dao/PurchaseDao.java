package com.ch.mall.ware.dao;

import com.ch.mall.ware.entity.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-07 10:29:24
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}
