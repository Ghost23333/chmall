package com.ch.mall.es_search.controller;

import com.ch.common.exception.BizCodeEnum;
import com.ch.common.to.es.SkuEsModel;
import com.ch.common.utils.R;
import com.ch.mall.es_search.service.ProductSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName SaveController.java
 * @createTime 2023年02月26日 14:36:00
 */
@RestController
@RequestMapping("/search/save")
public class SaveController {

    @Autowired
    ProductSaveService productSaveService;
    @RequestMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModelList){
        boolean status=false;
        try {
            status = productSaveService.productStatusUp(skuEsModelList);
        } catch (IOException e) {
            //log.error("商品上架错误{}",e);

            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }

        if(status){
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }else {
            return R.ok();
        }
    }
}
