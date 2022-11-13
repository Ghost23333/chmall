package com.ch.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ch.mall.product.entity.CategoryEntity;
import com.ch.mall.product.service.CategoryService;
import com.ch.common.utils.PageUtils;
import com.ch.common.utils.R;


/**
 * 商品三级分类
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 14:32:27
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 返回商品的所有列表，并且以树形结构的形式
     */
    @RequestMapping("/list/tree")
    //@RequiresPermissions("product:category:list")
    public R list() {
        List<CategoryEntity> entities = categoryService.listWithTree();
        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category) {
        //判断是否会冲突
        CategoryEntity parent = categoryService.getById(category.getParentCid());
        if(category.getName().equals(parent.getName())){
            return R.error();
        }else {
            List<CategoryEntity> categoryEntities = categoryService.selectBrothersByParentCid(category.getParentCid(), category.getCatLevel());
            for (CategoryEntity categoryEntity : categoryEntities) {
                if(categoryEntity.getName().equals(category.getName())){
                    return R.error(R.DUPLICATE_KEY_EXCEPTION,"重复菜单");
                }
            }
        }
        categoryService.save(category);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category) {
        //判断是否会冲突
        CategoryEntity temp = categoryService.getById(category.getCatId());
        CategoryEntity parent = categoryService.getById(temp.getParentCid());
        if(category.getName().equals(parent.getName())){
            return R.error(R.DUPLICATE_KEY_EXCEPTION,"重复菜单");
        }else {
            List<CategoryEntity> categoryEntities = categoryService.selectBrothersByParentCid(temp.getParentCid(), temp.getCatLevel());
            for (CategoryEntity categoryEntity : categoryEntities) {
                if(categoryEntity.getName().equals(category.getName())){
                    return R.error(R.DUPLICATE_KEY_EXCEPTION,"重复菜单");
                }
            }
        }
        categoryService.updateById(category);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds) {
        categoryService.removeMenusByIds(Arrays.asList(catIds));
        //categoryService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

    /**
     * @return 通过父亲id,获取包括自己在内的所有兄弟节点，也就是父亲节点的子节点
     */
    @GetMapping("/brothers")
    public R selectBrothersByParentCid(@RequestParam Long parentCid,@RequestParam Integer level){
        List<CategoryEntity> brothers = categoryService.selectBrothersByParentCid(parentCid, level);
        return R.ok().put("brothers",brothers);
    }
}
