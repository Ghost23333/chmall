package com.ch.mall.product.controller;

import com.ch.common.utils.PageUtils;
import com.ch.common.utils.R;
import com.ch.mall.product.entity.ProductAttrValueEntity;
import com.ch.mall.product.service.AttrService;
import com.ch.mall.product.service.ProductAttrValueService;
import com.ch.mall.product.vo.AttrRespVo;
import com.ch.mall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 商品属性
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 14:32:27
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * @return 获取spu规格
     * /product/attr/base/listforspu/{spuId}
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R listForSpu(@PathVariable("spuId") Long spuId){
        List<ProductAttrValueEntity> data = productAttrValueService.listForSpuAttrValue(spuId);
        return R.ok().put("data", data);
    }


    /**
     * @param params
     * @param catelogId
     * @return 返回基本属性
     */
    @GetMapping("/{attrType}/list/{catelogId}")
    public R AttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("attrType") String attrType) {
        PageUtils page = attrService.queryAttrPage(params, catelogId, attrType);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrRespVo attrRespVo = attrService.getAttrRespInfo(attrId);
        return R.ok().put("attr", attrRespVo);
    }

    /**
     * written
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attrvo) {
        attrService.saveAttrVo(attrvo);
        return R.ok();
    }

    /**
     * written
     * 修改 xxx
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attrvo) {
        attrService.updateAttrVo(attrvo);
        return R.ok();
    }

    @PostMapping("/update/{spuId}")
    //@RequiresPermissions("product:attr:update")
    public R updateSpuAttrs(@PathVariable("spuId") Long spuId,@RequestBody List<ProductAttrValueEntity> entities) {
        productAttrValueService.updateSpuAttrs(spuId,entities);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
