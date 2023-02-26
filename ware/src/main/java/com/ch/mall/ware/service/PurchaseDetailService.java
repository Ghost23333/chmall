package com.ch.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.ware.entity.PurchaseDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-07 10:29:24
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {


    void updateStatusByPurchaseIds(List<Long> newIds, Integer code);

    PageUtils queryPage(Map<String, Object> params);
}

