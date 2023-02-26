package com.ch.common.to;

import lombok.Data;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName SkuStockTo.java
 * @createTime 2023年02月24日 17:48:00
 */
@Data
public class SkuHasStockTo {
    private Long skuId;
    private Boolean hasStock;
}
