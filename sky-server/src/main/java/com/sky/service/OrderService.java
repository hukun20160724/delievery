package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

/**
 * ClassName: OrderService
 * Package: com.sky.service
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/8/24 15:15
 * @Version 1.0
 */
public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
