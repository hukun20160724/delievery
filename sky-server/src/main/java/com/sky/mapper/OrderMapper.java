package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
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
public interface OrderMapper {

    void insert(Orders orders);
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String outTradeNo);

    void update(Orders orders);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    @Select("select count(id) from orders where status=#{status}")
    Integer countStatus(Integer status);

    @Select("select * from orders where status=#{status} and order_time<#{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);
}
