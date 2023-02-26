package com.ch.mall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;


import com.ch.mall.ware.vo.PurchaseDoneVo;
import com.ch.mall.ware.vo.PurchaseMergeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ch.mall.ware.entity.PurchaseEntity;
import com.ch.mall.ware.service.PurchaseService;
import com.ch.common.utils.PageUtils;
import com.ch.common.utils.R;



/**
 * 采购信息
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-07 10:29:24
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;


    /**
     * 采购人员完成采购单
     */
    @PostMapping("/done")
    public R finish(@RequestBody PurchaseDoneVo vo){
        purchaseService.finish(vo);
        return R.ok();
    }
    /**
     * 采购人员领取采购单
     */
    @PostMapping("/received")
    public R receive(@RequestBody List<Long> ids){
        purchaseService.receive(ids);
        return R.ok();
    }

    /**
     * 合并采购单
     */
    @PostMapping("/merge")
    public R merge(@RequestBody PurchaseMergeVo vo){
        purchaseService.merge(vo);
        return R.ok();
    }


    /**
     * 获取未领取的采购单
     */
    @RequestMapping("/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryUnreceivedPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:purchase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
   //@RequiresPermissions("ware:purchase:info")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:purchase:save")
    public R save(@RequestBody PurchaseEntity purchase){
		purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:purchase:update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("ware:purchase:delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
