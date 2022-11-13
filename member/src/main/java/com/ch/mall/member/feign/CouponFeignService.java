package com.ch.mall.member.feign;

import com.ch.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author chenhong
 * @version 1.0.0
 * @ClassName CouponFeignService.java
 * @createTime 2022年11月07日 14:58:00
 */
@FeignClient("coupon")
public interface CouponFeignService {
    @GetMapping("coupon/coupon/member/list")
    public R memberCoupon();
}
