package com.ch.mall.coupon.service.impl;

import com.ch.common.to.SkuReductionTo;
import com.ch.mall.coupon.entity.MemberPriceEntity;
import com.ch.mall.coupon.entity.SkuLadderEntity;
import com.ch.mall.coupon.service.MemberPriceService;
import com.ch.mall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.common.utils.PageUtils;
import com.ch.common.utils.Query;

import com.ch.mall.coupon.dao.SkuFullReductionDao;
import com.ch.mall.coupon.entity.SkuFullReductionEntity;
import com.ch.mall.coupon.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    SkuLadderService skuLadderService;
    @Autowired
    MemberPriceService memberPriceService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
        //1. 保存满减打折
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(skuReductionTo,skuLadderEntity);
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
        if(skuLadderEntity.getFullCount() > 0){
            skuLadderService.save(skuLadderEntity);
        }
        //2. 保存满减价格
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo,skuFullReductionEntity);
        skuFullReductionEntity.setAddOther(skuReductionTo.getCountStatus());
        if(skuFullReductionEntity.getFullPrice().compareTo(new BigDecimal("0")) >0 ){
            this.save(skuFullReductionEntity);
        }
        //3. 会员价格
        List<SkuReductionTo.MemberPrice> memberPrice = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> memberPriceEntities = memberPrice.stream().map((item) -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
            memberPriceEntity.setMemberLevelId(item.getId());
            memberPriceEntity.setMemberPrice(item.getPrice());
            memberPriceEntity.setMemberLevelName(item.getName());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).filter(entity-> entity.getMemberPrice().compareTo(new BigDecimal("0")) > 0).collect(Collectors.toList());
        memberPriceService.saveBatch(memberPriceEntities);
    }

}