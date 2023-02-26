package com.ch.mall.ware.service.impl;

import com.ch.mall.ware.constant.PurchaseConstant;
import com.ch.mall.ware.entity.PurchaseDetailEntity;
import com.ch.mall.ware.service.PurchaseDetailService;
import com.ch.mall.ware.service.WareSkuService;
import com.ch.mall.ware.vo.PurchaseDoneVo;
import com.ch.mall.ware.vo.PurchaseMergeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.common.utils.PageUtils;
import com.ch.common.utils.Query;

import com.ch.mall.ware.dao.PurchaseDao;
import com.ch.mall.ware.entity.PurchaseEntity;
import com.ch.mall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService purchaseDetailService;
    @Autowired
    WareSkuService wareSkuService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnreceivedPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                    new QueryWrapper<PurchaseEntity>().eq("status",0).or().eq("status",1)
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void merge(PurchaseMergeVo vo) {
        Long purchaseId = vo.getPurchaseId();
        if(purchaseId == null){
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(PurchaseConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }
        List<Long> items = vo.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> purchaseDetailEntityList = items.stream().map((item) -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(PurchaseConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            purchaseDetailEntity.setId(item);
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(purchaseDetailEntityList);

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(finalPurchaseId);
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }

    @Transactional
    @Override
    public void receive(List<Long> ids) {

        //1. 确认当前采购单是新建或者已分配的状态
        List<PurchaseEntity> purchaseEntities = ids.stream().map((id) -> {
            return this.getById(id);
        }).filter((item) -> {
            return item.getStatus() == PurchaseConstant.PurchaseStatusEnum.CREATED.getCode() ||
                    item.getStatus() == PurchaseConstant.PurchaseStatusEnum.ASSIGNED.getCode();
        }).map((item)->{
            item.setStatus(PurchaseConstant.PurchaseStatusEnum.RECRIVE.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());

        //若不为空才进行下一步
        if(purchaseEntities.size()>= 1){
            //2. 改变采购单状态
            this.updateBatchById(purchaseEntities);
            //3. 改变采购单内所有采购项的状态
            List<Long> newIds = purchaseEntities.stream().map(PurchaseEntity::getId).collect(Collectors.toList());
            purchaseDetailService.updateStatusByPurchaseIds(newIds,PurchaseConstant.PurchaseDetailStatusEnum.BUYING.getCode());
        }

    }

    @Transactional
    @Override
    public void finish(PurchaseDoneVo vo) {

        Long id = vo.getId();
        //1. 改变采购单下采购项状态
        Boolean flag = true;
        List<PurchaseDetailEntity> updates = new ArrayList<>();
        List<PurchaseDoneVo.PurchaseItemDoneVo> items = vo.getItems();
        for (PurchaseDoneVo.PurchaseItemDoneVo item : items) {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            if(item.getStatus() == PurchaseConstant.PurchaseDetailStatusEnum.HASERROR.getCode()){
                flag = false;
            }else {
                //3. 将成功采购的商品入库
                PurchaseDetailEntity entity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(entity.getSkuId(),entity.getWareId(), entity.getSkuNum());
            }
            purchaseDetailEntity.setStatus(item.getStatus());
            purchaseDetailEntity.setId(item.getItemId());
            updates.add(purchaseDetailEntity);
        }
        purchaseDetailService.updateBatchById(updates);
        //2. 改变采购单状态
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(id);
        if(flag){
            purchaseEntity.setStatus(PurchaseConstant.PurchaseStatusEnum.FINISH.getCode());
        }else {
            purchaseEntity.setStatus(PurchaseConstant.PurchaseStatusEnum.HASERROR.getCode());
        }
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }


}