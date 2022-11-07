package com.ch.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-07 10:19:59
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

