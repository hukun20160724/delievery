package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.lettuce.core.ConnectionEvents;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: UserShoppingController
 * Package: com.sky.controller.user
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/8/24 11:30
 * @Version 1.0
 */
@RestController("shoppingCartController")
@RequestMapping("/user/shoppingCart")
@Api(tags = "C端-购物车接口")
@Slf4j
public class UserShoppingController {


    @Autowired
    ShoppingCartService shoppingCartService;

    // post /user/shoppingCart/add
    @PostMapping("/add")
    @ApiOperation(" add shopping cart")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info(" add itme to shopping cart.....");
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    // get/user/shoppingCart/list
    @ApiOperation(" get shopping cart")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list(){
        List<ShoppingCart> list=shoppingCartService.list();
        return Result.success(list);
    }

    // post /user/shoppingCart/sub
    @ApiOperation(" clean an item from cart")
    @DeleteMapping("/clean")
    public Result cleanShoppingCart(){
        shoppingCartService.clean();
        return Result.success();

    }

}
