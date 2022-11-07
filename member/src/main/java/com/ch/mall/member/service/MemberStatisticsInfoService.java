package com.ch.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.common.utils.PageUtils;
import com.ch.mall.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 23:11:52
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

