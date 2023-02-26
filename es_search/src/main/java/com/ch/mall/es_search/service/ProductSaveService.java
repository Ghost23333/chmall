package com.ch.mall.es_search.service;

import com.ch.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName SaveService.java
 * @createTime 2023年02月26日 14:40:00
 */
public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModelList) throws IOException;
}
