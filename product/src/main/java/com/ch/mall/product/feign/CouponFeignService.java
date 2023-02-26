package com.ch.mall.product.feign;

import com.ch.common.to.SkuReductionTo;
import com.ch.common.to.SpuBoundTo;
import com.ch.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName SpuFeignService.java
 * @Description TODO
 * @createTime 2022年12月09日 17:28:00
 */

@FeignClient("coupon")
public interface CouponFeignService {

    @PostMapping("coupon/spubounds/save")
    R saveSpuBounts(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("coupon/skufullreduction/saveInfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
