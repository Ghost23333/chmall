package com.ch.mall.product.feign;

import com.ch.common.to.SkuHasStockTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName WareFeignService.java
 * @createTime 2023年02月24日 18:22:00
 */
@FeignClient("ware")
public interface WareFeignService {
    @PostMapping("ware/waresku/skuStockList")
    List<SkuHasStockTo> skuHasStockList(@RequestBody List<Long> skuIds);
}
