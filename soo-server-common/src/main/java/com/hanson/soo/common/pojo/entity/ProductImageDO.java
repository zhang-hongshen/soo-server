package com.hanson.soo.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@ToString
@TableName("soo_product_image")
public class ProductImageDO {
    @TableId(type = IdType.AUTO)
    Long id;
    @TableField("product_id")
    String productId;
    @TableField("image_url")
    String url;
    @TableField("state")
    Boolean state;
    @TableField("create_time")
    LocalDateTime createTime;
    @TableField("update_time")
    LocalDateTime updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductImageDO that = (ProductImageDO) o;

        if (!productId.equals(that.productId)) return false;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }
}
