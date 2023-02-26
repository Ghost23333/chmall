package com.ch.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.ware.entity.PurchaseEntity;
import com.ch.mall.ware.vo.PurchaseDoneVo;
import com.ch.mall.ware.vo.PurchaseMergeVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-07 10:29:24
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryUnreceivedPage(Map<String, Object> params);

    void merge(PurchaseMergeVo vo);

    void receive(List<Long> ids);

    void finish(PurchaseDoneVo vo);
}

