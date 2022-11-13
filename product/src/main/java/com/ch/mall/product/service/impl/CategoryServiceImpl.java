package com.ch.mall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.common.utils.PageUtils;
import com.ch.common.utils.Query;

import com.ch.mall.product.dao.CategoryDao;
import com.ch.mall.product.entity.CategoryEntity;
import com.ch.mall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

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


}