package com.ch.mall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName PurchaseMergeVo.java
 * @Description TODO
 * @createTime 2022年12月21日 21:11:00
 */

@Data
public class PurchaseMergeVo {
    private Long purchaseId;
    private List<Long> items;
}
