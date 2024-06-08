package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
public interface OrderDetailMapper {

    void insertBatch(List<OrderDetail> list);
}
