package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: DishController
 * Package: com.sky.controller.admin
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/6/24 13:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @ApiOperation(" get the dish list")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info(" get the dish list ");
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation(" add a new dish")
    @PostMapping()
    public Result save(@RequestBody DishDTO dishDTO){
        log.info(" start add a new dish.......");
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    //DELETE http://localhost/api/dish?ids=71
    @ApiOperation(" delete a dish")
    @DeleteMapping()
    public Result delete(@RequestParam List<Long> ids){
        log.info(" delete a dish.......{}",ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }
}
