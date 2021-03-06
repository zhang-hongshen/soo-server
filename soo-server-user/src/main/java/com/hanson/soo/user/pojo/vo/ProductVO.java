package com.hanson.soo.user.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductVO implements Serializable {
    String productId;
    String productName;
    String destination;
    BigDecimal price;
    List<String> imageUrls;
    List<String> departures;
}
