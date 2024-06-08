package com.sky.controller.user;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: UserOrderController
 * Package: com.sky.controller.user
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/8/24 15:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/user/order")
@Api(tags = "user order API")
@Slf4j
public class UserOrderController {



    @Autowired
    OrderService orderService;

    @ApiOperation(" submit order")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info(" start submit an order.........{}",ordersSubmitDTO);
        OrderSubmitVO submitVO=orderService.submitOrder(ordersSubmitDTO);
        return Result.success(submitVO);
    }


}
