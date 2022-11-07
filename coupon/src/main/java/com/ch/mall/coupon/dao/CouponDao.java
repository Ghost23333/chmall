package com.ch.mall.coupon.dao;

import com.ch.mall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 19:47:37
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
