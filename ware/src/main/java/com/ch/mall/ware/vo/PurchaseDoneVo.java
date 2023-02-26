package com.ch.mall.ware.vo;

import jdk.internal.dynalink.linker.LinkerServices;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName PurchaseDoneVo.java
 * @Description TODO
 * @createTime 2022年12月23日 15:15:00
 */
@Data
public class PurchaseDoneVo {
    @NotNull
    private Long id;
    private List<PurchaseItemDoneVo> items;

    @Data
    public static class PurchaseItemDoneVo{
        private Long itemId;
        private Integer status;
        private String reason;
    }
}
