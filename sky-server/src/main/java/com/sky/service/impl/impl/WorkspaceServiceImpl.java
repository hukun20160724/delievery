package com.sky.service.impl.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: WorkspaceServiceImpl
 * Package: com.sky.service.impl.impl
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/11/24 12:04
 * @Version 1.0
 */
@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    DishMapper dishMapper;

    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {

        //new a map to set time
        Map map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        Integer orderCount = orderMapper.getOrderCount(map);//get the all order
        map.put("status", Orders.COMPLETED);
        Integer vali = orderMapper.getOrderCount(map);// get all valid order

        Double amount = orderMapper.getAmount(map);
        amount = amount == null ? 0.0 : amount;// 营业额
        Double orderCompleteRate = 0.0;
        Double unitPrice = 0.0;
        if (orderCount != 0 && vali != 0) {
            //
            orderCompleteRate = vali.doubleValue() / orderCount;
            // 客单价
            unitPrice = amount / vali;
        }
        Integer countUser = userMapper.countUser(map);
        return BusinessDataVO.builder()
                .turnover(amount)
                .orderCompletionRate(orderCompleteRate)
                .validOrderCount(vali)
                .unitPrice(unitPrice)
                .newUsers(countUser)
                .build();


    }

    @Override
    public SetmealOverViewVO getSetmealOverViewVO() {

        Integer sold=setmealMapper.countByStatus(StatusConstant.ENABLE);
       Integer discontinued= setmealMapper.countByStatus(StatusConstant.DISABLE);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    @Override
    public DishOverViewVO getoverviewDishes() {
        Integer sold=dishMapper.countByStatus(StatusConstant.ENABLE);
        Integer discontinued= dishMapper.countByStatus(StatusConstant.DISABLE);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    @Override
    public OrderOverViewVO getOrderOverView() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
        map.put("status", Orders.TO_BE_CONFIRMED);

        //待接单
        Integer waitingOrders = orderMapper.getOrderCount(map);

        //待派送
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.getOrderCount(map);

        //已完成
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.getOrderCount(map);

        //已取消
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.getOrderCount(map);

        //全部订单
        map.put("status", null);
        Integer allOrders = orderMapper.getOrderCount(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }
}
