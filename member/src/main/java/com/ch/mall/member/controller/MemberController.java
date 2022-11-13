package com.ch.mall.member.controller;

import java.util.Arrays;
import java.util.Map;


import com.ch.mall.member.feign.CouponFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ch.mall.member.entity.MemberEntity;
import com.ch.mall.member.service.MemberService;
import com.ch.common.utils.PageUtils;
import com.ch.common.utils.R;


/**
 * 会员
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 23:11:52
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    @GetMapping("/coupon/list")
    public R test() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("ch");
        R memberCoupons = couponFeignService.memberCoupon();
        return R.ok()
                .put("member", memberEntity)
                .put("coupons", memberCoupons.get("coupons"))
                .put("ch",memberCoupons.get("ch"));

    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
