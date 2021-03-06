package com.hanson.soo.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hanson.soo.admin.dao.AdminDao;
import com.hanson.soo.admin.dao.AdminTokenDao;
import com.hanson.soo.admin.pojo.RedisKeyPrefix;
import com.hanson.soo.admin.pojo.dto.AdminDTO;
import com.hanson.soo.admin.service.AdminService;
import com.hanson.soo.admin.utils.ConverterUtils;
import com.hanson.soo.admin.utils.TokenUtils;
import com.hanson.soo.common.pojo.entity.AdminDO;
import com.hanson.soo.common.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminTokenDao adminTokenDao;
    @Autowired
    private RedisService redisService;

    private static Logger logger = LogManager.getLogger(AdminService.class);

    @Override
    public String getAdminIdByToken(String token) {
        return adminTokenDao.getAdminIdByToken(token);
    }

    @Override
    public String refreshTokenByAdminId(String adminId) {
        String token = TokenUtils.createToken();
        // 更新或插入token
        adminTokenDao.insertOrUpdateTokenByAdminId(adminId, token);
        //更新后的token存入redis，value为用户信息
        redisService.set(RedisKeyPrefix.ADMIN_TOKEN.getPrefix() + token, "", 1, TimeUnit.HOURS);
        return token;
    }

    @Override
    public String getToken(AdminDTO adminDTO) {
        AdminDO adminDO = adminDao.selectOne(new LambdaQueryWrapper<AdminDO>()
                .eq(AdminDO::getName, adminDTO.getName())
                .eq(AdminDO::getPassword, adminDTO.getPassword()));
        if (adminDO == null) {
            logger.info("用户不存在");
            // 用户不存在
            return "";
        }
        return refreshTokenByAdminId(adminDO.getAdminId());
    }

    @Override
    public LocalDateTime getTokenUpdateTimeByAdminId(String adminId) {
        return adminTokenDao.getUpdateTimeByAdminId(adminId);
    }

    @Override
    public AdminDTO getAdminInfoByAdminId(String adminId){
        AdminDO adminDO = adminDao.selectOne(new LambdaUpdateWrapper<AdminDO>()
                .eq(AdminDO::getAdminId, adminId));
        return ConverterUtils.adminDO2DTO(adminDO);
    }

    @Override
    public boolean deleteToken(String token) {
        redisService.delete(RedisKeyPrefix.ADMIN_TOKEN.getPrefix() + token);
        return true;
    }
}
