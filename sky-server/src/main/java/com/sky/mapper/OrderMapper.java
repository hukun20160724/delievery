package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: OrderMapper
 * Package: com.sky.mapper
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/8/24 15:16
 * @Version 1.0
 */
@Mapper
public interface OrderMapper {

    void insert(Orders orders);
}
