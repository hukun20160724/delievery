package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;

import com.sky.service.DishService;
import com.sky.vo.DishVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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

    @ApiOperation(" get dish by id")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable("id") Long id){
        log.info(" get dish by id.......");
        DishVO dishVO=dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }


    @ApiOperation(" update a dish")
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO){
        log.info(" update a dish..........{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);

        return Result.success();
    }
    @ApiOperation(" update a dish status")
    @PostMapping("/status/{status}")
    public Result update(Long id,@PathVariable("status") Integer status){//需要在地址栏的参数 才加PathVariable
        log.info(" update a dish status..........{}",id,status);
        dishService.updateStatus(id,status);

        return Result.success();
    }

    // get method: http://localhost/api/dish/list?categoryId=17
    @ApiOperation("get dish list by categoryid")
    @GetMapping("/list")
    public  Result<List<Dish>> list(Long categoryId){
        List<Dish> dishes=dishService.getListBycategoryId(categoryId);
        return Result.success(dishes);
    }


}
