package com.ch.common.to.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName SkuEsModel.java
 * @createTime 2023年02月10日 21:35:00
 */
@Data
public class SkuEsModel {
    private Long skuId;
    private Long spuId;
    private String skuTitle;
        private BigDecimal skuPrice;
        private String skuImg;
        private Long saleCount;
        private Boolean hasStock;
        private Long hotScore;//热度值
        private Long brandId;
        private Long catalogId;
        private String brandName;
        private String brandImg;
        private String catalogName;
    private List<Attrs> attrs;
    @Data
    public static class Attrs{
        private Long attrId;
        private String attrName;
        private String attrValue;
    }
}
