package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: ShopController
 * Package: com.sky.controller.admin
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/7/24 16:42
 * @Version 1.0
 */
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "shop")
@Slf4j
public class ShopController {


    public static final String key="SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation(" set shop status")
    public Result setStatus(@PathVariable Integer status){
        log.info(" set shop status.........{}",status==1?"open":"close");
        redisTemplate.opsForValue().set(key,status);
        return Result.success();

    }
    @GetMapping("/status")
    @ApiOperation(" get shop status")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(key);
        log.info(" get shop status.........{}",status==1?"open":"close");
        return Result.success(status);

    }


}
