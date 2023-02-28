package com.ch.mall.product.feign;

import com.ch.common.to.es.SkuEsModel;
import com.ch.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName SearchFeignService.java
 * @createTime 2023年02月26日 15:32:00
 */

@FeignClient("esSearch")
public interface SearchFeignService {

    @RequestMapping("/search/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModelList);
}
