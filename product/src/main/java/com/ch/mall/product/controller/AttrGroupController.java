package com.ch.mall.product.controller;

import com.ch.common.utils.PageUtils;
import com.ch.common.utils.R;
import com.ch.mall.product.entity.AttrEntity;
import com.ch.mall.product.entity.AttrGroupEntity;
import com.ch.mall.product.service.AttrAttrgroupRelationService;
import com.ch.mall.product.service.AttrGroupService;
import com.ch.mall.product.service.AttrService;
import com.ch.mall.product.service.CategoryService;
import com.ch.mall.product.vo.AttrAttrGroupRelationVo;
import com.ch.mall.product.vo.AttrGroupWithAttrsRespVo;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 属性分组
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 14:32:27
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    AttrGroupService attrGroupService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AttrService attrService;

    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;


    /**
     * 获取分类下所有分组&关联属
     * @return
     */
    //product/attrgroup/{catelogId}/withattr
    @GetMapping("{catelogId}/withattr")
    public R listAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId){
        List<AttrGroupWithAttrsRespVo> data = attrGroupService.selectAttrGroupWithAttrs(catelogId);
        return R.ok().put("data",data);
    }


    //product/attrgroup/attr/relation
    @PostMapping("/attr/relation")
    public R addRelations(@RequestBody List<AttrAttrGroupRelationVo> vos){
        attrAttrgroupRelationService.saveBatch(vos);
        return R.ok();
    }
    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId) {
        //PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 根据分组id获取当前分组id下的所有关联属性
     */
    @GetMapping("/{attrGroupId}/attr/relation")
    public R listAttrGroupRelation(@PathVariable("attrGroupId") Long attrGroupId){
        List<AttrEntity> data = attrService.queryRelationAttrsByGroupId(attrGroupId);
        return R.ok().put("data",data);

    }
    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.selectCatelogPath(catelogId);
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }


    /**
     * 删除分组的属性关联
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelations(@RequestBody List<AttrAttrGroupRelationVo> vos){
        attrGroupService.deleteRelations(vos);
        return R.ok();
    }

    /**
     * 获取本分类里尚没被关联的属性
     */
    @GetMapping("/{attrGroupId}/noattr/relation")
    public R listNotRelations(@RequestParam Map<String, Object> params,@PathVariable("attrGroupId") Long attrGroupId){
        PageUtils page = attrService.queryNotRelationAttrsPage(params,attrGroupId);
        return R.ok().put("page",page);

    }

}
