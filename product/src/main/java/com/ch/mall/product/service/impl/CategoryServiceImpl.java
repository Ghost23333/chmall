package com.ch.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.common.exception.BizCodeEnum;
import com.ch.common.utils.PageUtils;
import com.ch.common.utils.Query;
import com.ch.common.utils.R;
import com.ch.mall.product.dao.CategoryDao;
import com.ch.mall.product.entity.CategoryBrandRelationEntity;
import com.ch.mall.product.entity.CategoryEntity;
import com.ch.mall.product.service.CategoryBrandRelationService;
import com.ch.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 1 查出所有的分类
     * 2 组装商品分类为具有父子关系的树形分类结构
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有商品分类
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        //组装树形结构
        //一级菜单
        List<CategoryEntity> level1Menu = categoryEntities.stream().filter((categoryEntity -> {
            return categoryEntity.getParentCid() == 0;
        })).map((menu -> {
            menu.setChildren(getChildren(menu, categoryEntities));
            return menu;
        })).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());


        return level1Menu;
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {
        //TODO 检查当前删除的菜单是否在其他地方被引用
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * @param root
     * @param all  返回root节点的子菜单
     *             递归
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter((categoryEntity -> {
            return Objects.equals(categoryEntity.getParentCid(), root.getCatId());
        })).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }

    @Override
    public List<CategoryEntity> selectBrothersByParentCid(Long parentCid, Integer level) {
        return categoryDao.selectBrothersByParentCid(parentCid, level);
    }

    /**
     * 找到catelogid的完整路径
     * @param catelogId
     * @return 【父 子 孙】
     * 因为最多只有三级分类，所以这里采用迭代的方法，不采用递归
     */
    @Override
    public Long[] selectCatelogPath(Long catelogId) {
        List<Long> path = new ArrayList<>();
        CategoryEntity category = this.getById(catelogId);
        if(category.getParentCid() != 0){
            CategoryEntity parent = this.getById(category.getParentCid());
            if(parent.getParentCid() != 0){
                path.add(parent.getParentCid());//爷
            }
            path.add(parent.getCatId());//父
        }
        path.add(catelogId);//自己
        return path.toArray(new Long[0]);

    }

    @Override
    @Transactional
    public R updateCascade(CategoryEntity category) {
        //判断是否会冲突
        CategoryEntity temp = this.getById(category.getCatId());
        CategoryEntity parent = this.getById(temp.getParentCid());
        if(category.getName().equals(parent.getName())){
            return R.error(BizCodeEnum.DUPLICATE_KEY_EXCEPTION.getCode(), BizCodeEnum.DUPLICATE_KEY_EXCEPTION.getMsg());
        }else {
            List<CategoryEntity> categoryEntities = this.selectBrothersByParentCid(temp.getParentCid(), temp.getCatLevel());
            for (CategoryEntity categoryEntity : categoryEntities) {
                if(categoryEntity.getName().equals(category.getName())){
                    return R.error(BizCodeEnum.DUPLICATE_KEY_EXCEPTION.getCode(), BizCodeEnum.DUPLICATE_KEY_EXCEPTION.getMsg());
                }
            }
        }
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
        return R.ok();
    }

    @Override
    @Transactional
    public R saveCategory(CategoryEntity category) {
        //判断是否会冲突
        CategoryEntity parent = this.getById(category.getParentCid());
        if(category.getName().equals(parent.getName())){
            return R.error();
        }else {
            List<CategoryEntity> categoryEntities = this.selectBrothersByParentCid(category.getParentCid(), category.getCatLevel());
            for (CategoryEntity categoryEntity : categoryEntities) {
                if(categoryEntity.getName().equals(category.getName())){
                    return R.error(BizCodeEnum.DUPLICATE_KEY_EXCEPTION.getCode(), BizCodeEnum.DUPLICATE_KEY_EXCEPTION.getMsg());
                }
            }
        }
        this.save(category);
        return R.ok();
    }


}