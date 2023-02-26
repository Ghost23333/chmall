package com.ch.mall.ware.feign;

import com.ch.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName ProductFeign.java
 * @Description TODO
 * @createTime 2022年12月23日 17:24:00
 */
@FeignClient("product")
public interface ProductFeignService {
    @RequestMapping("/product/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);
}
