package com.ch.mall.member.dao;

import com.ch.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author chenhong
 * @email chenhong@gmail.com
 * @date 2022-11-05 23:11:52
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
