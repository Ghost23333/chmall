package com.ch.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.common.utils.PageUtils;
import com.ch.common.utils.Query;
import com.ch.mall.product.constant.ProductConstant;
import com.ch.mall.product.dao.AttrAttrgroupRelationDao;
import com.ch.mall.product.dao.AttrDao;
import com.ch.mall.product.dao.AttrGroupDao;
import com.ch.mall.product.dao.CategoryDao;
import com.ch.mall.product.entity.*;
import com.ch.mall.product.service.AttrService;
import com.ch.mall.product.service.CategoryService;
import com.ch.mall.product.vo.AttrRespVo;
import com.ch.mall.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Attr;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    @Autowired
    AttrAttrgroupRelationDao relationDao;
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttrVo(AttrVo attrvo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrvo, attrEntity);
        //保存基本数据 AttrEntity
        this.save(attrEntity);
        //保存关联关系
        if(attrvo.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) && attrvo.getAttrGroupId() != null ){
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
                attrAttrgroupRelationEntity.setAttrGroupId(attrvo.getAttrGroupId());
                attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
                relationDao.insert(attrAttrgroupRelationEntity);
        }
    }

    @Transactional
    @Override
    public void updateAttrVo(AttrVo attrvo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrvo, attrEntity);
        //更新基本数据 AttrEntity
        this.updateById(attrEntity);
        //更新关联信息
        if(attrvo.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())){
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attrvo.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrvo.getAttrId());
            relationDao.update(attrAttrgroupRelationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrvo.getAttrId()));
        }
    }

    /**
     * 根据分组id查询 其关联的所有属性
     * @param attrGroupId
     * @return
     */
    @Override
    @Transactional
    public List<AttrEntity> queryRelationAttrsByGroupId(Long attrGroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));
        List<Long> attrIds = relationEntities.stream().map((relationEntitie) -> relationEntitie.getAttrId()).collect(Collectors.toList());
        if(attrIds.size() == 0){
            return null;
        }
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        return (List<AttrEntity>) attrEntities;
    }

    @Override
    public PageUtils queryNotRelationAttrsPage(Map<String, Object> params, Long attrGroupId) {
        //先找出分组的分类id catelogId
        Long catelogId = attrGroupDao.selectCatelogIdById(attrGroupId);

        //根据catelogId 将分类下的所有分组找出来
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        //将分类分组的attrGroupId提取出来
        List<Long> attrGroupIds = attrGroupEntities.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());
        //根据attrGroupIds将relation里面对应的数据提取出来,为已经关联过的属性
        List<AttrAttrgroupRelationEntity> AlreadyRelationEntityList = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", attrGroupIds));
        //将已经关联的属性实体中将attrId提取出来
        List<Long> attrIds = AlreadyRelationEntityList.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        //将已经关联过的id过滤
        //根据catelogId找出所有与之相关的 base属性
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId)
                .eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        //过滤已经关联过的属性
        if(attrIds.size() > 0){
           wrapper.notIn("attr_id",attrIds);
        }
        //模糊查询
        if(!StringUtils.isEmpty(key)){
            wrapper.and((w) ->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 返回指定attrIds中为可搜索的attr的id
     * @param attrIds
     * @return
     */
    @Override
    public List<Long> selectSearchAttrs(List<Long> attrIds) {

        return baseMapper.selectSearchAttrs(attrIds);
    }

    @Override
    public PageUtils queryAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        int attrTypeCode;
        if(attrType.equalsIgnoreCase("base")){
            attrTypeCode = ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode();
        }else{
            attrTypeCode = ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode();
        }
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("attr_type", attrTypeCode);
        String key = (String) params.get("key");
        if (catelogId != 0) {
            wrapper.eq("catelog_id", catelogId);
        }
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> {
                obj.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper

        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respList = records.stream().map((attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            //设置分组的name,只有基本属性才有分组信息
            if ("base".equalsIgnoreCase(attrType)) {
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if (attrAttrgroupRelationEntity != null && attrAttrgroupRelationEntity.getAttrGroupId() != null) {
                    Long attrGroupId = attrAttrgroupRelationEntity.getAttrGroupId();
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            //设置分类name
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        })).collect(Collectors.toList());
        pageUtils.setList(respList);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrRespInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity, attrRespVo);

        if(attrEntity.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())){
            //设置分组信息
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if (attrAttrgroupRelationEntity != null) {
                Long attrGroupId = attrAttrgroupRelationEntity.getAttrGroupId();
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                attrRespVo.setAttrGroupId(attrGroupId);
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
        }
        //设置分类信息
        Long[] catelogPath = categoryService.selectCatelogPath(attrEntity.getCatelogId());
        CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
        if (categoryEntity != null) {
            attrRespVo.setCatelogName(categoryEntity.getName());
        }
        attrRespVo.setCatelogPath(catelogPath);
        return attrRespVo;
    }

}