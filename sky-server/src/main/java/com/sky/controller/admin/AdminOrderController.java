package com.sky.controller.admin;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.mapper.OrderMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: AdminOrderController
 * Package: com.sky.controller.admin
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/9/24 11:19
 * @Version 1.0
 */
@RestController
@Api(tags = "admin order ")
@RequestMapping("/admin/order")
@Slf4j
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    // get /admin/order/conditionSearch
    @ApiOperation(" search orders")
    @GetMapping("/conditionSearch")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO){
        PageResult pageResult=orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);

    }

    //Path： /admin/order/statistics
    //Method： GET
    @ApiOperation(" order status count")
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> getStatistics(){
        OrderStatisticsVO orderStatisticsVO=orderService.getStatistics();
        return Result.success(orderStatisticsVO);
    }

    //Path： /admin/order/details/{id}
    //Method： GET

    @ApiOperation(" get order details")
    @GetMapping("/details/{id}")
    public Result<OrderVO> getDetails(@PathVariable("id") Long id){
        OrderVO details = orderService.details(id);
        return Result.success(details);
    }
}
