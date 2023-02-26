package com.ch.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName SpuBoundTo.java
 * @createTime 2022年12月09日 17:34:00
 */

@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
