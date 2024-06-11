package com.sky.Task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: OrderTask
 * Package: com.sky.Task
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/10/24 14:13
 * @Version 1.0
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 0/5 * * * ?")//5 min
    public void processTimeoutOrder(){
        log.info(" handle timeout order.....");
        // check the order status
        List<Orders> byStatusAndOrderTimeLT = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, LocalDateTime.now().plusMinutes(-15));
        if (byStatusAndOrderTimeLT!=null && byStatusAndOrderTimeLT.size()>0){
            for (Orders order : byStatusAndOrderTimeLT) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("time out...Cancel");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * ? ")//each day 1 am
    public void processDelivery(){
        log.info(" handle Delivery order.....");
        // check the order status
        List<Orders> byStatusAndOrderTimeLT = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().plusMinutes(-60));
        if (byStatusAndOrderTimeLT!=null && byStatusAndOrderTimeLT.size()>0){
            for (Orders order : byStatusAndOrderTimeLT) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
