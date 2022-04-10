package com.hanson.soo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hanson.soo.common.dao.CartDao;
import com.hanson.soo.common.pojo.entity.CartDO;
import com.hanson.soo.user.pojo.dto.CartDTO;
import com.hanson.soo.user.service.CartService;
import com.hanson.soo.user.utils.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;

    @Override
    @Transactional
    public boolean insertCart(CartDTO cartDTO) {
        CartDO cartDO = cartDao.selectOne(new LambdaQueryWrapper<CartDO>()
                .eq(CartDO::getProductId, cartDTO.getProductId())
                .eq(CartDO::getDeparture, cartDTO.getDeparture())
                .eq(CartDO::getUserId, cartDTO.getUserId()));
        if(cartDO != null){
            cartDO.setNum(cartDO.getNum() + cartDTO.getNum());
            return cartDao.updateById(cartDO) > 0;
        }
        return cartDao.insert(ConverterUtils.cartDTO2DO(cartDTO)) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartDTO> listCarts(String userId){
        List<CartDO> cartDOS = cartDao.selectList(new LambdaQueryWrapper<CartDO>()
                .eq(CartDO::getUserId, userId)
                .orderByDesc(CartDO::getCreateTime));
        List<CartDTO> cartDTOS = new ArrayList<>(cartDOS.size());
        cartDOS.forEach(cartDO -> cartDTOS.add(ConverterUtils.cartDO2DTO(cartDO)));
        return cartDTOS;
    }

    @Override
    @Transactional
    public boolean deleteCartsByUserIdAndProductId(String userId, List<String> productIds){
        int count = 0;
        for(String productId : productIds){
            count += cartDao.delete(new LambdaQueryWrapper<CartDO>()
                    .eq(CartDO::getUserId, userId)
                    .eq(CartDO::getProductId, productId));
        }
        return count == productIds.size();
    }

    @Override
    @Transactional
    public boolean updateCartByUserId(String userId, List<CartDTO> cartDTOS){
        for(CartDTO cartDTO : cartDTOS){
            cartDao.update(ConverterUtils.cartDTO2DO(cartDTO),new LambdaUpdateWrapper<CartDO>()
                    .eq(CartDO::getUserId, userId)
                    .eq(CartDO::getProductId, cartDTO.getProductId()));
        }
        return true;
    }
}