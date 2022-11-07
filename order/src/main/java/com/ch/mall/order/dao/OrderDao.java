package com.ch.mall.order.dao;

import com.ch.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-07 10:19:59
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
