package com.hanson.soo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hanson.soo.common.pojo.entity.ConsigneeDO;
import com.hanson.soo.user.dao.ConsigneeDao;
import com.hanson.soo.user.pojo.dto.ConsigneeDTO;
import com.hanson.soo.user.service.ConsigneeService;
import com.hanson.soo.user.utils.ConverterUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsigneeServiceImpl implements ConsigneeService {
    @Autowired
    private ConsigneeDao consigneeDao;

    @Override
    @Transactional(readOnly = true)
    public List<ConsigneeDTO> listConsigneesByUserId(String userId){
        List<ConsigneeDO> consigneeDOs = consigneeDao.selectList(new LambdaQueryWrapper<ConsigneeDO>()
                .eq(ConsigneeDO::getUserId, userId));
        return consigneeDOs.stream()
                .map(ConverterUtils::consigneeDO2DTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean saveConsignee(String userId, ConsigneeDTO consigneeDTO) {
        ConsigneeDO consigneeDO =  consigneeDao.selectById(consigneeDTO.getId());
        if(consigneeDO == null){
            return consigneeDao.insert(ConverterUtils.consigneeDTO2DO(consigneeDTO)) > 0;
        }
        BeanUtils.copyProperties(consigneeDTO, consigneeDO);
        return  consigneeDao.updateById(consigneeDO) > 0;
    }

    @Override
    public boolean deleteConsigneeById(Long id){
        return consigneeDao.deleteById(id) > 0;
    }
}
