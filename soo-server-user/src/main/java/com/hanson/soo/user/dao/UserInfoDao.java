package com.hanson.soo.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hanson.soo.common.pojo.entity.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoDao extends BaseMapper<UserInfoDO> {
    String getPasswordByUserId(String userId);
    String getUsernameByUserId(String userId);
    int updatePasswordByUserId(String userId, String password);
}

