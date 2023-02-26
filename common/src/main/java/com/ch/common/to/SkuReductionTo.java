package com.ch.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName SkuReductionTo.java
 * @createTime 2022年12月09日 19:31:00
 */
@Data
public class SkuReductionTo {
    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;

    @Data
    public static class MemberPrice {
        private Long id;
        private String name;
        private BigDecimal price;
    }
}
