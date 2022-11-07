package com.ch.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-07 10:29:24
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

