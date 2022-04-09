package com.hanson.soo.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanson.soo.user.pojo.dto.OrderDetailDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderVO {
    String orderId;
    List<OrderDetailDTO> orderDetails;
    BigDecimal totalAmount;
    String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date paymentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;
}
