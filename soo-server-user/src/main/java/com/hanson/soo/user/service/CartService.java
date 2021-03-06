package com.hanson.soo.user.service;


import com.hanson.soo.common.pojo.dto.PageDTO;
import com.hanson.soo.user.pojo.dto.CartDTO;

import java.util.List;

public interface CartService {
    boolean insertCart(CartDTO cartDTO);
    PageDTO<List<CartDTO>> listCart(int current, int pageSize, String userId);
    boolean deleteCartsByUserIdAndProductId(String userId, List<String> productIds);
    boolean updateCartByUserId(String userId, List<CartDTO> cartDTOS);
}
