package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: SetmealController
 * Package: com.sky.controller.admin
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/7/24 13:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @ApiOperation("add new setmeal...")
    @PostMapping()
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setmealService.saveWithDish(setmealDTO);
        return Result.success();

    }
    // GET http://localhost/api/setmeal/page?page=1&pageSize=10&status=
    @ApiOperation(" get the setmeal list...")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){// use a new vo to accept the data
        PageResult pageResult=setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);

    }


}
