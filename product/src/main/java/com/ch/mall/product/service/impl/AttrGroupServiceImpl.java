package com.ch.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.common.utils.PageUtils;
import com.ch.common.utils.Query;
import com.ch.mall.product.dao.AttrAttrgroupRelationDao;
import com.ch.mall.product.dao.AttrGroupDao;
import com.ch.mall.product.entity.AttrAttrgroupRelationEntity;
import com.ch.mall.product.entity.AttrEntity;
import com.ch.mall.product.entity.AttrGroupEntity;
import com.ch.mall.product.service.AttrGroupService;
import com.ch.mall.product.service.AttrService;
import com.ch.mall.product.vo.AttrAttrGroupRelationVo;
import com.ch.mall.product.vo.AttrGroupWithAttrsRespVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    AttrService attrService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        IPage<AttrGroupEntity> page;
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        //如果关键字不为空
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> obj.eq("attr_group_id", key).or().like("attr_group_name", key));
        }
        if (catelogId != 0) {
            wrapper.eq("catelog_id", catelogId);
        }
        page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public void deleteRelations(List<AttrAttrGroupRelationVo> vos) {
        List<AttrAttrgroupRelationEntity> relationEntityList = vos.stream().map((item) -> {
            AttrAttrgroupRelationEntity entity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, entity);
            return entity;
        }).collect(Collectors.toList());
        attrAttrgroupRelationDao.deleteRelations(relationEntityList);
    }

    @Override
    @Transactional
    public List<AttrGroupWithAttrsRespVo> selectAttrGroupWithAttrs(Long catelogId) {
        //首先根据catelogId查找到所有的attrGroup
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> attrGroupIds = attrGroupEntities.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());

        //对每一个respVo插入对应的属性数组
        List<AttrGroupWithAttrsRespVo> attrGroupWithAttrsRespVos = attrGroupEntities.stream().map((entity) -> {
            AttrGroupWithAttrsRespVo attrGroupWithAttrsRespVo = new AttrGroupWithAttrsRespVo();
            BeanUtils.copyProperties(entity, attrGroupWithAttrsRespVo);
            attrGroupWithAttrsRespVo.setAttrs(attrService.queryRelationAttrsByGroupId(entity.getAttrGroupId()));
            return attrGroupWithAttrsRespVo;
        }).collect(Collectors.toList());
        return attrGroupWithAttrsRespVos;
    }

}